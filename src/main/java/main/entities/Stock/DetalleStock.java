package main.entities.Stock;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Ingredientes.EnumMedida;
import main.entities.Ingredientes.Ingrediente;
import main.entities.Pedidos.Pedido;
import main.entities.Productos.ArticuloMenu;
import main.entities.Productos.ArticuloVenta;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "detalle_stock", schema = "buen_sabor")
public class DetalleStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "medida")
    private EnumMedida medida;
    @Column(name = "subtotal")
    private double subTotal;
    @OneToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;
    @OneToOne
    @JoinColumn(name = "id_articulo")
    private ArticuloVenta articuloVenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stock_entrante")
    private StockEntrante stockEntrante;
}
