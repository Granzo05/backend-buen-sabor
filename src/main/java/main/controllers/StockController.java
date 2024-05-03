package main.controllers;

import main.entities.Ingredientes.IngredienteMenu;
import main.entities.Productos.ArticuloMenu;
import main.entities.Stock.StockIngredientes;
import main.repositories.ArticuloMenuRepository;
import main.repositories.IngredienteRepository;
import main.repositories.StockIngredientesRepository;
import main.repositories.SucursalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.Optional;

@RestController
public class StockController {
    private final StockIngredientesRepository stockIngredientesRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ArticuloMenuRepository articuloMenuRepository;
    private final SucursalRepository sucursalRepository;

    public StockController(StockIngredientesRepository stockIngredientesRepository, IngredienteRepository ingredienteRepository, ArticuloMenuRepository articuloMenuRepository, SucursalRepository sucursalRepository) {
        this.stockIngredientesRepository = stockIngredientesRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.articuloMenuRepository = articuloMenuRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @GetMapping("/stockIngredientes/ingredientes")
    public Set<StockIngredientes> getStockIngredientes() {
        List<StockIngredientes> stockIngredientes = stockIngredientesRepository.findAll();
        if (stockIngredientes.isEmpty()) {
            return null;
        }

        return (Set<StockIngredientes>) stockIngredientes;
    }


    @GetMapping("/stockIngredientes/{nombre}/{cantidad}")
    public ResponseEntity<String> getStockPorNombre(@PathVariable("nombre") String nombre, @PathVariable("cantidad") int cantidad) {
        // Recibimos el nombre del menu y la cantidad pedida del mismo
        Optional<ArticuloMenu> menu = articuloMenuRepository.findByName(nombre);

        if (!menu.isEmpty()) {
            // Buscamos ingrediente por ingrediente a ver si el stockIngredientes es suficiente
            for (IngredienteMenu ingrediente : menu.get().getIngredientesMenu()) {
                // Mediante el ingrediente accedemos al stockIngredientes del mismo
                Optional<StockIngredientes> stockEncontrado = stockIngredientesRepository.findStockByProductName(ingrediente.getIngrediente().getNombre());

                if (stockEncontrado.isPresent()) {
                    System.out.println("Stock db cliente medida: " + ingrediente.getMedida() + " y cantidad: " + ingrediente.getCantidad());
                    // Si el ingrediente tiene la misma medida que el stockIngredientes almacenado entonces se calcula a la misma medida.

                    // Si hay stockIngredientes, entonces se multiplica por la cantidad del menu requerida, si para un menu necesito 300 gramos de X ingrediente, si estoy pidiendo
                    // 4 menus, entonces necesitaría en total 1200 gramos de eso

                    if (stockEncontrado.get().getIngrediente().getStock().getMedida().equals(ingrediente.getMedida()) && stockEncontrado.get().getCantidadActual() < ingrediente.getCantidad() * cantidad) {
                        return new ResponseEntity<>("El stockIngredientes no es suficiente", HttpStatus.BAD_REQUEST);

                    } else if (!stockEncontrado.get().getIngrediente().getStock().getMedida().equals("Kg") && ingrediente.getMedida().equals("Gramos")) {

                        // Si almacené el ingrediente por KG, y necesito 300 gramos en el menu, entonces convierto de KG a gramos para calcularlo en la misma medida
                        if (stockEncontrado.get().getCantidadActual() * 1000 < ingrediente.getCantidad() * cantidad) {
                            return new ResponseEntity<>("El stockIngredientes no es suficiente", HttpStatus.BAD_REQUEST);
                        }

                    }
                }
            }
        } else {
            return new ResponseEntity<>("El menú no existe", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("El stockIngredientes es suficiente", HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/stockIngredientes/check")
    public ResponseEntity<String> checkStock(@RequestParam(value = "menus") Set<ArticuloMenu> articuloMenus) {
        System.out.println(articuloMenus);
        for (ArticuloMenu articuloMenu : articuloMenus) {
            for (IngredienteMenu ingrediente : articuloMenu.getIngredientesMenu()) {
                Optional<StockIngredientes> stockEncontrado = stockIngredientesRepository.findStockByProductName(ingrediente.getIngrediente().getNombre());

                if (stockEncontrado.isPresent()) {
                    System.out.println("Stock db medida: " + stockEncontrado.get().getIngrediente().getStock().getMedida() + " y cantidad: " + stockEncontrado.get().getCantidadActual());
                    System.out.println("Stock db cliente medida: " + ingrediente.getMedida() + " y cantidad: " + ingrediente.getCantidad());
                    // Si el ingrediente tiene la misma medida que el stockIngredientes almacenado entonces se calcula a la misma medida.
                    if (stockEncontrado.get().getIngrediente().getStock().getMedida().equals(ingrediente.getMedida()) && stockEncontrado.get().getCantidadActual() < ingrediente.getCantidad()) {
                        return new ResponseEntity<>("El stockIngredientes no es suficiente", HttpStatus.BAD_REQUEST);
                    } else if (!stockEncontrado.get().getIngrediente().getStock().getMedida().equals("Kg") && ingrediente.getMedida().equals("Gramos")) {
                        // Si almacené el ingrediente por KG, y necesito 300 gramos en el menu, entonces convierto de KG a gramos para calcularlo en la misma medida
                        if (stockEncontrado.get().getCantidadActual() * 1000 < ingrediente.getCantidad()) {
                            return new ResponseEntity<>("El stockIngredientes no es suficiente", HttpStatus.BAD_REQUEST);
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>("El stockIngredientes es suficiente", HttpStatus.CREATED);
    }

/*
    @Transactional
    @PostMapping("/stockIngredientes/create")
    public ResponseEntity<String> crearStock(@RequestBody StockIngredientes stockIngredientesDetail) {
        // Busco el ingrediente en la base de datos
        Ingrediente ingredienteDB = ingredienteRepository.findByName(stockIngredientesDetail.getIngrediente().getNombre());

        // Si no hay stockIngredientes del producto cargado, entonces creamos uno nuevo. Caso contrario utilizamos y editamos el que ya está cargado en la base de datos
        if (ingredienteDB == null) {
            Optional<Sucursal> sucursal = sucursalRepository.findById(stockIngredientesDetail.getSucursal().getId());

            StockIngredientes stockIngredientes = new StockIngredientes(stockIngredientesDetail.getCantidadActual(), stockIngredientesDetail.getCantidadMinima(), stockIngredientesDetail.getCantidadMaxima(), sucursal.get());

            stockIngredientes.getFechaIngreso().add((FechaStock) stockIngredientesDetail.getFechaIngreso());

            Ingrediente ingrediente = Ingrediente.builder()
                    .nombre(stockIngredientesDetail.getIngrediente().getNombre())
                    .build();

            // Asignamos el ingrediente a este nuevo stockIngredientes
            //stockIngredientes.setIngrediente(ingrediente);

            // Guardamos nuevamente el ingredienteDB con los posibles datos nuevos
            ingredienteRepository.save(ingrediente);

            // Finalmente se guarda y se devuelve un mensaje con el ok
            //stockIngredientesRepository.save(stockIngredientes);

            return new ResponseEntity<>("El stockIngredientes ha sido añadido correctamente", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("El stockIngredientes ya existe", HttpStatus.FOUND);
    }

    @PutMapping("/stockIngredientes/update")
    public ResponseEntity<String> actualizarStock(@RequestBody StockIngredientes stockIngredientesDetail) {
        Ingrediente ingredienteDB = ingredienteRepository.findByName(stockIngredientesDetail.getIngrediente().getNombre());

        // Busco el stockIngredientes de ese ingrediente
        Optional<StockIngredientes> stockEncontrado = stockIngredientesRepository.findByIdIngrediente(ingredienteDB.getId());

        if (stockEncontrado.isEmpty()) {
            return new ResponseEntity<>("El stockIngredientes no existe", HttpStatus.FOUND);
        }

        stockIngredientesRepository.save(stockEncontrado.get());
        return new ResponseEntity<>("El stockIngredientes ha sido añadido correctamente", HttpStatus.CREATED);
    }

    @DeleteMapping("stockIngredientes/delete")
    public ResponseEntity<String> borrarStock(@RequestBody StockIngredientes stockIngredientes) {
        Optional<StockIngredientes> stockEncontrado = stockIngredientesRepository.findStockByProductName(stockIngredientes.getIngrediente().getNombre());
        if (stockEncontrado.isEmpty()) {
            return new ResponseEntity<>("El stockIngredientes ya ha sido borrado previamente", HttpStatus.BAD_REQUEST);
        }

        stockEncontrado.get().setBorrado("SI");
        stockIngredientesRepository.save(stockEncontrado.get());
        return new ResponseEntity<>("El stockIngredientes ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }

 */
}
