package main.entities.Stock;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Ingredientes.EnumMedida;
import main.entities.Productos.ArticuloVenta;
import main.entities.Restaurante.Sucursal;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "stock_articulos", schema = "buen_sabor")
public class StockArticuloVenta extends Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_articulo")
    private ArticuloVenta articuloVenta;


    public StockArticuloVenta(int cantidadActual, int cantidadMinima, int cantidadMaxima, Sucursal sucursal, ArticuloVenta articuloVenta, EnumMedida medida,  double precioCompra) {
        super(precioCompra, cantidadActual, cantidadMinima, cantidadMaxima, medida, sucursal);
        this.articuloVenta = articuloVenta;
    }
}