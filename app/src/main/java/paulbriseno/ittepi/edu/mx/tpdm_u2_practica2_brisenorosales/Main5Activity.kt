package paulbriseno.ittepi.edu.mx.tpdm_u2_practica2_brisenorosales

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main5Activity : AppCompatActivity() {
    var insertar : Button?=null
    var actualizar : Button?=null
    var buscar : Button?=null
    var eliminar : Button?=null

    var fecha : EditText?=null
    var total: EditText?=null
    var idcliente : EditText?=null



    var basedato=BaseDatos(this,"practica98",null,1)                    // C O M P  R  A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        insertar=findViewById(R.id.btncomInsert)
        buscar=findViewById(R.id.btncomBuscar)
        actualizar=findViewById(R.id.btncomActualizar)
        eliminar=findViewById(R.id.btncomEliminar)


        fecha=findViewById(R.id.comFecha)
        total=findViewById(R.id.comTotal)
        idcliente=findViewById(R.id.comidCliente)

        insertar?.setOnClickListener {
            insertar()
        }
        buscar?.setOnClickListener {
            pedirID()
        }
        actualizar?.setOnClickListener {
            alertaActualizar()
        }
        eliminar?.setOnClickListener {
            alertaEliminar()
        }
    }
    fun alertaEliminar(){

        var campo =EditText(this)

        campo.inputType= InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("ATENCION!!!").setMessage("!Estas seguro que desea BORRAR?\n ESCRIBA ID: " ).setView(campo)
            .setNeutralButton("No Borrar"){dialogInterface, i ->
                limpiarCampos()
            }.setPositiveButton("BORRAR"){dialogInterface, i ->
                if (validar(campo) == false) {

                    Toast.makeText(this@Main5Activity, "ERROR CAMPO ID VACIO", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                eliminar(campo.text.toString())  }.show()

    }

    fun eliminar(id:String){

        try{
            var transaccion=basedato.writableDatabase
            var SQL= "DELETE FROM COMPRA WHERE ID="+id

            transaccion.execSQL(SQL)
            transaccion.close()

            limpiarCampos()
            Toast.makeText(this,"Dato Eliminado", Toast.LENGTH_LONG).show()

        }catch (err: SQLiteException){
            mensaje("ERROr","NO SE LOGRO ELEMINAR")
        }


    }

    fun alertaActualizar(){
        AlertDialog.Builder(this).setTitle("Atencion").setMessage("!Estas seguro que desea aplciar cambios?")
            .setNeutralButton("No Actualizar"){dialogInterface, i ->

                limpiarCampos()
            }.setPositiveButton("si Actualizar"){dialogInterface, i -> actualizar()  }.show()

    }
    fun actualizar() {
        try {
            var transaccion = basedato.writableDatabase
            var SQL = "UPDATE COMPRA SET FECHA = 'Campo1', TOTAL ='Campo2', IDCLIENTE= 'Campo3'  WHERE ID="+ idPaActualizar

            if (validaCampos() == false) {
                mensaje("ERROR", "algun campo de texto esta vacio")
                return
            }
            SQL=SQL.replace("Campo1",fecha?.text.toString())
            SQL=SQL.replace("Campo2",total?.text.toString())
            SQL=SQL.replace("Campo3",idcliente?.text.toString())
            transaccion.execSQL((SQL))
            transaccion.close()
            limpiarCampos()
            mensaje("EXITO", "SE ACTUALIZO CORRECTAMENTE")

        } catch (err: SQLiteException){
            mensaje("ERROR", "NO SE ACTUALIZO")
        }
    }


    fun buscar(id:String){
        try {
            var transaccion = basedato.readableDatabase
            var SQL="SELECT * FROM COMPRA WHERE ID="+id

            var resultado = transaccion.rawQuery(SQL,null )

            if(resultado.moveToFirst()==true){

                fecha?.setText(resultado.getString(1))
                total?.setText(resultado.getString(2))
                idcliente?.setText(resultado.getString(3))
                idPaActualizar=id.toInt()

                Toast.makeText(this@Main5Activity,"SI SE ENCONTRO"+ idPaActualizar, Toast.LENGTH_LONG)

            }
            else {
                mensaje("ATENCION","AL PARECER NO ENCONTRE EL ID")
            }
            transaccion.close()
        }catch (err: SQLiteException){
            mensaje("ERROR","NO SE PUDO REALIZAR EL SELECT")
        }
    }
    fun pedirID(){      /// pedirID(etiquetaBoton:String)
        var campo =EditText(this)

        campo.inputType= InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("ATENCION").setMessage("Escriba el ID: ").setView(campo)
            .setNeutralButton("CANCELAR"){dialogInterface, i ->  }
            .setPositiveButton("BUSCAR") { dialogInterface, i ->

                if (validar(campo) == false) {

                    Toast.makeText(this@Main5Activity, "ERROR CAMPO ID VACIO", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }

                buscar(campo.text.toString())
            }.show()
    }
    fun  validar(vcampo :EditText):Boolean{

        if(vcampo.text.toString().isEmpty()){
            return false
        }else{return true}

    }
    companion object{
        var idPaActualizar =0
    }
    fun insertar(){
        try {
            var transaacion=basedato.writableDatabase
            var SQL="INSERT INTO COMPRA VALUES(NULL,'Campo1','Campo2','Campo3')"
            if(validaCampos()==false){

                mensaje("ERROR!!!","REVISE QUE LOS CAMPOS NO ESTEN VACIOS")
                return
            }
            SQL=SQL.replace("Campo1",fecha?.text.toString())
            SQL=SQL.replace("Campo2",total?.text.toString())
            SQL=SQL.replace("Campo3",idcliente?.text.toString())
            transaacion.close()
            mensaje("EXITOSO!!","AGREGADO")
            limpiarCampos()


        }catch (err: SQLiteException){
            mensaje("Error!!!","No se pudo insertar el registro, Revise los campos")

        }



    }

    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }


    fun validaCampos(): Boolean{
        if(fecha?.text!!.toString().isEmpty()||total?.text!!.isEmpty()||idcliente?.text!!.isEmpty()){
            return false
        }else{
            return true
        }
    }
    fun limpiarCampos(){
        fecha?.setText("")
        total?.setText("")
        idcliente?.setText("")

    }
}
