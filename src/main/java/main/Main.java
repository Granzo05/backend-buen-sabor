package main;

import main.entities.Cliente.Cliente;
import main.entities.Domicilio.*;
import main.entities.Factura.EnumMetodoPago;
import main.entities.Factura.EnumTipoFactura;
import main.entities.Factura.Factura;
import main.entities.Ingredientes.Ingrediente;
import main.entities.Ingredientes.IngredienteMenu;
import main.entities.Ingredientes.EnumMedida;
import main.entities.Pedidos.*;
import main.entities.Productos.*;
import main.entities.Restaurante.Empleado;
import main.entities.Restaurante.Empresa;
import main.entities.Restaurante.FechaContratacionEmpleado;
import main.entities.Restaurante.Sucursal;
import main.entities.Stock.DetalleStock;
import main.entities.Stock.StockArticuloVenta;
import main.entities.Stock.StockEntrante;
import main.entities.Stock.StockIngredientes;
import main.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class Main {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ArticuloVentaRepository articuloVentaRepository;

    @Autowired
    private ArticuloMenuRepository articuloMenuRepository;

    @Autowired
    private ImagenesProductoRepository imagenesProductoRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleStockRepository detalleStockRepository;

    @Autowired
    private StockEntranteRepository stockEntranteRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    CommandLineRunner init() {
        return args -> {
            try {

                Pais pais = new Pais();
                pais.setNombre("Argentina");

                Provincia provincia1 = new Provincia();
                provincia1.setNombre("Mendoza");

                Departamento departamento = new Departamento();
                departamento.setProvincia(provincia1);
                departamento.setNombre("Godoy Cruz");

                Localidad localidad1 = new Localidad();
                localidad1.setNombre("Villa Hipodromo");

                Localidad localidad2 = new Localidad();
                localidad2.setNombre("Las Tortugas");

                Departamento departamento1 = new Departamento();
                departamento1.setProvincia(provincia1);
                departamento1.setNombre("Luján de Cuyo");

                Localidad localidad3 = new Localidad();
                localidad3.setNombre("Las Compuertas");

                Localidad localidad4 = new Localidad();
                localidad4.setNombre("Chacras");

                localidad1.setDepartamento(departamento);
                localidad2.setDepartamento(departamento);
                localidad3.setDepartamento(departamento1);
                localidad4.setDepartamento(departamento1);

                departamento.getLocalidades().add(localidad1);
                departamento.getLocalidades().add(localidad2);

                departamento1.getLocalidades().add(localidad3);
                departamento1.getLocalidades().add(localidad4);

                provincia1.getDepartamentos().add(departamento);
                provincia1.getDepartamentos().add(departamento1);

                provincia1.setPais(pais);

                pais.getProvincias().add(provincia1);

                paisRepository.save(pais);

                Empresa buenSabor = new Empresa();
                buenSabor.setCuit(30555222);
                buenSabor.setRazonSocial("Buen sabor");

                empresaRepository.save(buenSabor);

                Sucursal sucursalChacras = new Sucursal();
                sucursalChacras.setDomicilio(new Domicilio("San Martín", 1422, 5744, localidad1, sucursalChacras));
                sucursalChacras.setHorarioApertura(LocalTime.of(17,0));
                sucursalChacras.setHorarioCierre(LocalTime.of(23,0));
                sucursalChacras.setContraseña("123");
                sucursalChacras.setEmail("buenSaborChacras@gmail.com");
                sucursalChacras.setPrivilegios("negocio");
                sucursalChacras.setTelefono(2613856699l);
                sucursalChacras.setEmpresa(buenSabor);
                sucursalChacras.getLocalidadesDisponiblesDelivery().add(localidad3);
                sucursalChacras.getLocalidadesDisponiblesDelivery().add(localidad4);

                sucursalChacras = sucursalRepository.save(sucursalChacras);


                Empleado empleado = new Empleado();
                empleado.setCuil("40123555");
                empleado.setContraseña("123");
                empleado.setEmail("pepeEmpleado@gmail.com");
                empleado.setNombre("Pepe empleado");
                empleado.setPrivilegios("empleado");
                empleado.setTelefono(2613859966l);
                empleado.getDomicilios().add(new Domicilio("San Juan", 123, 1544, localidad1, empleado));
                empleado.setFechaNacimiento(new Date(2000 - 1900, 5, 10));
                empleado.getFechaContratacion().add(new FechaContratacionEmpleado(Calendar.getInstance().getTime(), empleado));
                empleado.setSucursal(sucursalChacras);
                empleadoRepository.save(empleado);



                Sucursal sucursalGodoyCruz = new Sucursal();
                sucursalGodoyCruz.setDomicilio(new Domicilio("Sarmiento", 777, 5001, localidad2, sucursalGodoyCruz));
                sucursalGodoyCruz.setHorarioApertura(LocalTime.of(16,0));
                sucursalGodoyCruz.setHorarioCierre(LocalTime.of(22,0));
                sucursalGodoyCruz.setContraseña("123");
                sucursalGodoyCruz.setEmail("buenSaborGC@gmail.com");
                sucursalGodoyCruz.setPrivilegios("negocio");
                sucursalGodoyCruz.setTelefono(261344119l);
                sucursalGodoyCruz.setEmpresa(buenSabor);
                sucursalGodoyCruz.getLocalidadesDisponiblesDelivery().add(localidad1);

                sucursalGodoyCruz = sucursalRepository.save(sucursalGodoyCruz);

                Empleado empleado1 = new Empleado();
                empleado1.setCuil("37158447");
                empleado1.setContraseña("123");
                empleado1.setEmail("pepeEmpleado1@gmail.com");
                empleado1.setNombre("Pepe empleado 1");
                empleado1.setPrivilegios("empleado");
                empleado1.setTelefono(2613859966l);
                empleado1.getDomicilios().add(new Domicilio("San Pepe", 123, 1544, localidad1, empleado1));
                empleado1.setFechaNacimiento(new Date(1991 - 1900, 5, 10));
                empleado1.getFechaContratacion().add(new FechaContratacionEmpleado(Calendar.getInstance().getTime(), empleado1));
                empleado1.setSucursal(sucursalGodoyCruz);

                empleadoRepository.save(empleado1);

                Empleado empleado2 = new Empleado();
                empleado2.setCuil("32155444");
                empleado2.setContraseña("123");
                empleado2.setEmail("pepeEmpleado2@gmail.com");
                empleado2.setNombre("Pepe empleado 2");
                empleado2.setPrivilegios("empleado");
                empleado2.setTelefono(2635554444l);
                empleado2.getDomicilios().add(new Domicilio("San Pedro", 123, 1544, localidad2, empleado2));
                empleado2.getFechaContratacion().add(new FechaContratacionEmpleado(Calendar.getInstance().getTime(), empleado2));
                empleado2.setFechaNacimiento(new Date(1975 - 1900, 5, 10));
                empleado2.setSucursal(sucursalGodoyCruz);

                empleadoRepository.save(empleado2);

                buenSabor.getSucursales().add(sucursalChacras);
                buenSabor.getSucursales().add(sucursalGodoyCruz);



                ArticuloVenta cocaCola = new ArticuloVenta();
                cocaCola.setNombre("Coca cola");
                cocaCola.setTipo(EnumTipoArticuloVenta.BEBIDA_SIN_ALCOHOL);
                cocaCola.setMedida(EnumMedida.CENTIMETROS_CUBICOS);
                cocaCola.setCantidadMedida(450);
                cocaCola.setStock(new StockArticuloVenta(20, 5, 100, sucursalGodoyCruz, cocaCola, EnumMedida.UNIDADES, 650.0));
                cocaCola.setPrecioVenta(1100);
                ImagenesProducto imagen = ImagenesProducto.builder()
                        .nombre("Coca cola")
                        .ruta("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg")
                        .articuloVenta(cocaCola)
                        .build();
                cocaCola.getImagenes().add(imagen);

                cocaCola = articuloVentaRepository.save(cocaCola);

                Ingrediente harina = new Ingrediente();
                StockIngredientes stockHarina = new StockIngredientes(50, 10, 100, sucursalGodoyCruz, harina, EnumMedida.KILOGRAMOS, 400);
                harina.setStock(stockHarina);
                harina.setNombre("Harina");

                Ingrediente queso = new Ingrediente();
                StockIngredientes stockQueso = new StockIngredientes(5, 2, 10, sucursalGodoyCruz, queso, EnumMedida.KILOGRAMOS, 800);
                queso.setStock(stockQueso);
                queso.setNombre("Queso");

                Ingrediente tomate = new Ingrediente();
                tomate.setBorrado("NO");
                StockIngredientes stockTomate = new StockIngredientes(15, 2, 40, sucursalGodoyCruz, tomate, EnumMedida.KILOGRAMOS, 800);
                tomate.setStock(stockTomate);
                tomate.setNombre("Tomate");

                ingredienteRepository.save(harina);
                ingredienteRepository.save(queso);
                ingredienteRepository.save(tomate);


                DetalleStock detalleStock = DetalleStock.builder()
                        .articuloVenta(cocaCola)
                        .cantidad(40)
                        .medida(EnumMedida.UNIDADES)
                        .subTotal(40000)
                        .build();

                DetalleStock detalleStock1 = DetalleStock.builder()
                        .ingrediente(tomate)
                        .cantidad(10)
                        .medida(EnumMedida.KILOGRAMOS)
                        .subTotal(7000)
                        .build();

                StockEntrante stockEntrante = new StockEntrante();
                stockEntrante.setSucursal(sucursalGodoyCruz);
                stockEntrante.setFechaLlegada(new Date(2024 - 1900, 6, 25));

                stockEntrante.getDetallesStock().add(detalleStock);
                stockEntrante.getDetallesStock().add(detalleStock1);

                for (DetalleStock detalle: stockEntrante.getDetallesStock()) {
                    stockEntrante.setTotal(stockEntrante.getTotal() + detalle.getSubTotal());
                }

                stockEntrante = stockEntranteRepository.save(stockEntrante);

                detalleStock1.setStockEntrante(stockEntrante);
                detalleStock.setStockEntrante(stockEntrante);

                detalleStockRepository.save(detalleStock1);
                detalleStockRepository.save(detalleStock);

                ArticuloMenu pizzaMuzza = new ArticuloMenu();
                pizzaMuzza.setNombre("Pizza muzzarella");
                pizzaMuzza.setTipo(EnumTipoArticuloComida.PIZZAS);
                pizzaMuzza.setDescripcion("Pizza muzzarella muy rica");
                pizzaMuzza.setTiempoCoccion(30);
                pizzaMuzza.setComensales(2);
                pizzaMuzza.setPrecioVenta(4500.0);
                pizzaMuzza.getIngredientesMenu().add(new IngredienteMenu(300, EnumMedida.GRAMOS, queso, pizzaMuzza));
                pizzaMuzza.getIngredientesMenu().add(new IngredienteMenu(600, EnumMedida.GRAMOS, harina, pizzaMuzza));
                ImagenesProducto imagen2 = ImagenesProducto.builder()
                        .nombre("Pizza muzza")
                        .ruta("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg")
                        .articuloMenu(pizzaMuzza)
                        .build();
                pizzaMuzza.getImagenes().add(imagen2);

                ArticuloMenu pizzaNapolitana = new ArticuloMenu();
                pizzaNapolitana.setNombre("Pizza napolitana");
                pizzaNapolitana.setTipo(EnumTipoArticuloComida.PIZZAS);
                pizzaNapolitana.setDescripcion("Pizza napolitana muy rica");
                pizzaNapolitana.setTiempoCoccion(30);
                pizzaNapolitana.setComensales(2);
                pizzaNapolitana.setPrecioVenta(4500.0);
                pizzaNapolitana.getIngredientesMenu().add(new IngredienteMenu(300, EnumMedida.GRAMOS, queso, pizzaNapolitana));
                pizzaNapolitana.getIngredientesMenu().add(new IngredienteMenu(600, EnumMedida.GRAMOS, harina, pizzaNapolitana));
                pizzaNapolitana.getIngredientesMenu().add(new IngredienteMenu(500, EnumMedida.GRAMOS, tomate, pizzaNapolitana));
                ImagenesProducto imagen1 = ImagenesProducto.builder()
                        .nombre("Pizza muzza")
                        .ruta("https://assets.elgourmet.com/wp-content/uploads/2023/03/8metlvp345_portada-pizza-1024x686.jpg.webp")
                        .articuloMenu(pizzaNapolitana)
                        .build();
                pizzaNapolitana.getImagenes().add(imagen1);

                articuloMenuRepository.save(pizzaMuzza);
                pizzaNapolitana = articuloMenuRepository.save(pizzaNapolitana);

                Promocion promocionApertura = new Promocion();
                promocionApertura.getArticulosVenta().add(cocaCola);
                promocionApertura.getArticulosMenu().add(pizzaNapolitana);
                promocionApertura.setNombre("Promocion de apertura");
                promocionApertura.setDescripcion("Promo disponible todo el finde");
                promocionApertura.setFechaDesde(new Date(2024, 4, 26));
                promocionApertura.setFechaHasta(new Date(2024, 4, 30));
                promocionApertura.setPrecio(1500);
                promocionApertura.getSucursales().add(sucursalGodoyCruz);
                promocionRepository.save(promocionApertura);

                Cliente cliente = new Cliente();
                cliente.setNombre("Pepe pepito");
                cliente.setContraseña("123456");
                cliente.setEmail("pepe123@gmail.com");
                cliente.setFechaNacimiento(new Date(1985, Calendar.NOVEMBER, 10));
                cliente.setTelefono(2615558888L);

                Domicilio domicilio = new Domicilio("La calle de pepe", 123, 2511, localidad1, cliente);
                cliente.getDomicilios().add(domicilio);

                clienteRepository.save(cliente);

                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setEstado(EnumEstadoPedido.ENTREGADOS);
                pedido.setHoraFinalizacion("13:00");
                pedido.setTipoEnvio(EnumTipoEnvio.TIENDA);

                pedidoRepository.save(pedido);

                DetallesPedido detalle1 = new DetallesPedido(2, pizzaMuzza.getPrecioVenta() * 2, pizzaMuzza, pedido);
                DetallesPedido detalle2 = new DetallesPedido(1, pizzaNapolitana.getPrecioVenta() * 1, pizzaNapolitana, pedido);
                DetallesPedido detalle3 = new DetallesPedido(1, cocaCola.getPrecioVenta() * 1, cocaCola, pedido);

                detallePedidoRepository.save(detalle1);
                detallePedidoRepository.save(detalle2);
                detallePedidoRepository.save(detalle3);

                Factura factura = Factura.builder()
                        .tipoFactura(EnumTipoFactura.B)
                        .metodoPago(EnumMetodoPago.EFECTIVO)
                        .pedido(pedido)
                        .build();

                double total = 0;

                for (DetallesPedido detalle: pedido.getDetallesPedido()) {
                    total += detalle.getSubTotal();
                }

                factura.setTotal(total);

                facturaRepository.save(factura);

                System.out.println("Carga completa");
            } catch (Exception e) {
                System.out.println("Error " + e);
            }

        };
    }
}