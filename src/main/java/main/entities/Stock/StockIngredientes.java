package main.entities.Stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.entities.Ingredientes.EnumMedida;
import main.entities.Ingredientes.Ingrediente;
import main.entities.Productos.ArticuloVenta;
import main.entities.Restaurante.Sucursal;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock_ingredientes", schema = "buen_sabor")
public class StockIngredientes extends Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    public StockIngredientes(int cantidadActual, int cantidadMinima, int cantidadMaxima, Sucursal sucursal, Ingrediente ingrediente, EnumMedida medida, double precioCompra) {
        super(precioCompra, cantidadActual, cantidadMinima, cantidadMaxima, medida);
        this.ingrediente = ingrediente;
        this.sucursal = sucursal;
    }

}
