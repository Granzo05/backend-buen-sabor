package main.entities.Factura;

import jakarta.persistence.*;
import lombok.*;
import main.entities.Pedidos.Pedido;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "facturas", schema = "buen_sabor")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tipo_factura")
    private EnumTipoFactura tipoFactura;
    @Column(name = "metodo_pago")
    private EnumMetodoPago metodoPago;
    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaFacturacion;
    @Column(name = "total")
    private double total;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}