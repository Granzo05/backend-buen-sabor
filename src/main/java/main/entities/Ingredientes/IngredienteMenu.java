package main.entities.Ingredientes;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Productos.ArticuloMenu;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "ingredientes_menu", schema = "buen_sabor")
public class IngredienteMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cantidad_ingrediente")
    private int cantidad;
    @Column(name = "medida")
    private EnumMedida medida;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu")
    private ArticuloMenu articuloMenu;

    public IngredienteMenu(int cantidad, EnumMedida medida, Ingrediente ingrediente, ArticuloMenu articuloMenu) {
        this.cantidad = cantidad;
        this.medida = medida;
        this.ingrediente = ingrediente;
        this.articuloMenu = articuloMenu;
    }
}