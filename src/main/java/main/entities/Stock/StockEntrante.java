package main.entities.Stock;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Cliente.Cliente;
import main.entities.Factura.Factura;
import main.entities.Pedidos.DetallesPedido;
import main.entities.Pedidos.EnumEstadoPedido;
import main.entities.Pedidos.EnumTipoEnvio;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "stock_entrante", schema = "buen_sabor")
public class StockEntrante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "fecha_llegada", updatable = false, nullable = false)
    public Date fechaLlegada;
    @JsonIgnore
    @Column(name = "borrado")
    private String borrado = "NO";
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "stockEntrante")
    private Set<DetalleStock> detallesStock = new HashSet<>();


}