package paulbriseno.ittepi.edu.mx.tpdm_u2_practica2_brisenorosales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var almacen :Button?=null
    var empresa :Button?=null
    var cliente: Button?=null
    var compra :Button?=null
    var detacompra:Button?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        almacen=findViewById(R.id.almacen)
        empresa=findViewById(R.id.empresa)
        cliente=findViewById(R.id.cliente)
        compra=findViewById(R.id.compra)
        detacompra=findViewById(R.id.detaCompra)


        almacen?.setOnClickListener {
            val ventanaAlmacen = Intent(this,Main7Activity::class.java)
            startActivity(ventanaAlmacen)
        }
        empresa?.setOnClickListener {
            val ventanEmpresa=Intent(this,Main3Activity::class.java)
            startActivity(ventanEmpresa)

        }
        cliente?.setOnClickListener {
            val ventanacliente =Intent(this,Main4Activity::class.java)
            startActivity(ventanacliente)

        }
        compra?.setOnClickListener {
            val ventanaCompra=Intent(this,Main5Activity::class.java)
            startActivity(ventanaCompra)

        }
        detacompra?.setOnClickListener {
            val ventanadetacompra=Intent(this,Main6Activity::class.java)
            startActivity(ventanadetacompra)

        }
    }
}
