package paulbriseno.ittepi.edu.mx.tpdm_u2_practica2_brisenorosales

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main3Activity : AppCompatActivity() {
    var insertar : Button?=null
    var actualizar : Button?=null
    var buscar : Button?=null
    var eliminar : Button?=null

    var descripcion : EditText?=null
    var domicilio: EditText?=null

    var basedato=BaseDatos(this,"practica98",null,1)  //           E M P R E S A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        insertar=findViewById(R.id.btnEmpInsert)
        buscar=findViewById(R.id.btnEmpBuscar)
        actualizar=findViewById(R.id.btnEmpActualizar)
        eliminar=findViewById(R.id.btnEmpEliminar)


        descripcion=findViewById(R.id.empDescripcion)
        domicilio=findViewById(R.id.empDomicilio)

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

                    Toast.makeText(this@Main3Activity, "ERROR CAMPO ID VACIO", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                eliminar(campo.text.toString())  }.show()

    }

    fun eliminar(id:String){

        try{
            var transaccion=basedato.writableDatabase
            var SQL= "DELETE FROM EMPRESA WHERE ID="+id
           // domicilio?.setText(SQL)

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
            var SQL = "UPDATE EMPRESA SET DESCRPCION = 'Campo1', DOMICILIO ='Campo2' WHERE ID="+ idPaActualizar

            if (validaCampos() == false) {
                mensaje("ERROR", "algun campo de texto esta vacio")
                return
            }
            SQL=SQL.replace("Campo1",descripcion?.text.toString())
            SQL=SQL.replace("Campo2",domicilio?.text.toString())

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
            var SQL="SELECT * FROM EMPRESA WHERE ID="+1

            var resultado = transaccion.rawQuery(SQL,null )

            if(resultado.moveToFirst()==true){

                descripcion?.setText(resultado.getString(1))
                domicilio?.setText(resultado.getString(2))
                idPaActualizar=id.toInt()

                Toast.makeText(this@Main3Activity,"SI SE ENCONTRO"+ idPaActualizar, Toast.LENGTH_LONG)

            }
            else {
                mensaje("ATENCION","AL PARECER NO ENCONTRE EL ID")
            }
            transaccion.close()
        }catch (err: SQLiteException){
            mensaje("ERROR","NO SE PUDO REALIZAR EL SELECT")
        }
    }
    fun pedirID(){
        var campo =EditText(this)

        campo.inputType= InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("ATENCION").setMessage("Escriba el ID: ").setView(campo)
            .setNeutralButton("CANCELAR"){dialogInterface, i ->  }
            .setPositiveButton("BUSCAR") { dialogInterface, i ->

                if (validar(campo) == false) {

                    Toast.makeText(this@Main3Activity, "ERROR CAMPO ID VACIO", Toast.LENGTH_LONG).show()
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
            var SQL="INSERT INTO EMPRESA VALUES(NULL,'Campo1','Campo2')"
            if(validaCampos()==false){

                mensaje("ERROR!!!","REVISE QUE LOS CAMPOS NO ESTEN VACIOS")
                return
            }
            SQL=SQL.replace("Campo1",descripcion?.text.toString())
            SQL=SQL.replace("Campo2",domicilio?.text.toString())

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
        if(descripcion?.text!!.toString().isEmpty()||domicilio?.text!!.isEmpty()){
            return false
        }else{
            return true
        }
    }
    fun limpiarCampos(){
        domicilio?.setText("")
        descripcion?.setText("")

    }
}
