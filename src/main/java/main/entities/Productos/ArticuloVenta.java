package main.entities.Productos;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Ingredientes.EnumMedida;
import main.entities.Stock.Stock;
import main.entities.Stock.StockArticuloVenta;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "articulos_venta", schema = "buen_sabor")
public class ArticuloVenta extends Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private EnumTipoArticuloVenta tipo;
    @Column(name = "medida")
    private EnumMedida medida;
    @Column(name = "cantidad_medida")
    private int cantidadMedida;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private StockArticuloVenta stock;
    @OneToMany(mappedBy = "articuloVenta", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ImagenesProducto> imagenes = new HashSet<>();
    @ManyToMany(mappedBy = "articulosVenta", fetch = FetchType.LAZY)
    private Set<Promocion> promociones = new HashSet<>();
}
