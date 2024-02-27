package com.example.practica4

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.sqrt

enum class Operaciones {
    NING,
    MAS,
    MENOS,
    MULTIPLICACION,
    DIVISION,
    MODULO,
    MASMENOS,
    POTENCIA,
    LOGARITMO,
    RAIZ
}



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculadora)

        val hist = ArrayList<Operation>()

        //Se recuperan todos los elementos de la vista
        val resultado:Button = findViewById(R.id.res)
        val ac:Button = findViewById(R.id.AC)
        val potencia:Button = findViewById(R.id.potencia)
        val logaritmo:Button = findViewById(R.id.log)
        val raiz:Button = findViewById(R.id.raiz)
        val modulo:Button = findViewById(R.id.modulo)
        val masmenos:Button = findViewById(R.id.masmenos)
        val division:Button = findViewById(R.id.division)
        val siete:Button = findViewById(R.id.siete)
        val ocho:Button = findViewById(R.id.ocho)
        val nueve:Button = findViewById(R.id.nueve)
        val cero:Button = findViewById(R.id.cero)
        val uno:Button = findViewById(R.id.uno)
        val dos:Button = findViewById(R.id.dos)
        val tres:Button = findViewById(R.id.tres)
        val cuatro:Button = findViewById(R.id.cuatro)
        val cinco:Button = findViewById(R.id.cinco)
        val seis:Button = findViewById(R.id.seis)
        val punto:Button = findViewById(R.id.punto)
        val mas:Button = findViewById(R.id.mas)
        val menos:Button = findViewById(R.id.menos)
        val multiplicacion:Button = findViewById(R.id.multiplicacion)
        val igual:Button = findViewById(R.id.igual)
        val historial:Button = findViewById(R.id.history)

        historial.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("hist", hist)
            startActivity(intent)
        }

        //Se asignan acciones a los botones de los números
        habilitarNumero(cero, 0, resultado)
        habilitarNumero(uno, 1, resultado)
        habilitarNumero(dos, 2, resultado)
        habilitarNumero(tres, 3, resultado)
        habilitarNumero(cuatro, 4, resultado)
        habilitarNumero(cinco, 5, resultado)
        habilitarNumero(seis, 6, resultado)
        habilitarNumero(siete, 7, resultado)
        habilitarNumero(ocho, 8, resultado)
        habilitarNumero(nueve, 9, resultado)
        //Configuración del porta papeles para poder copiar el resultado
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        //Configuración de la operación en curso
        var operacion:Operaciones = Operaciones.NING
        //Valores para ir guardando los resultados
        var valor1 = 0.0
        var valor2: Double

        //Función de apoyo para calcular operaciones continuas
        fun calcularOperacion(nuevaOperacion: Operaciones){

            valor2 = resultado.text.toString().toDouble() //Obtiene el valor 2 de la calculadora
            val resTpm = obtenerResultado(operacion, valor1, valor2)
            hist.add(Operation(valor1, valor2, nuevaOperacion, resTpm))
            valor1 = resTpm //Realiza el cáclulo, nuevo valor1
            valor2 = 0.0                                        //Reinicia el valor2
            resultado.text = "0"                                //Reinicia la vista
            operacion = nuevaOperacion                          //Coloca la nueva operación
        }



        //Configuración de los botones de operaciones
        modulo.setOnClickListener {
            if (operacion == Operaciones.NING) {                //Si no hay operación previa...
                operacion = Operaciones.MODULO                  //Asigna una operación
                valor1 = resultado.text.toString().toDouble()   //Guarda el valor entrado en valor1
                resultado.text = "0"                            //Reinicia la entrada para el valor2
            }else{
                calcularOperacion(Operaciones.MODULO)           //Si hay una operación, la realiza
            }                                                   //La lógica es igual en los demás
        }

        mas.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.MAS
                valor1 = resultado.text.toString().toDouble()
                resultado.text = "0"
            }else{
                calcularOperacion(Operaciones.MAS)
            }
        }

        menos.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.MENOS
                valor1 = resultado.text.toString().toDouble()
                resultado.text = "0"
            }else{
                calcularOperacion(Operaciones.MENOS)
            }
        }

        multiplicacion.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.MULTIPLICACION
                valor1 = resultado.text.toString().toDouble()
                resultado.text = "0"
            }else{
                calcularOperacion(Operaciones.MULTIPLICACION)
            }
        }

        division.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.DIVISION
                valor1 = resultado.text.toString().toDouble()
                resultado.text = "0"
            }else{
                calcularOperacion(Operaciones.DIVISION)
            }
        }

        masmenos.setOnClickListener {//Unicamente invierte por *-1 el valor actual
            resultado.text = obtenerResultado(Operaciones.MASMENOS,
                resultado.text.toString().toDouble()).toString()
        }

        igual.setOnClickListener {//El igual obtiene el resultado
            valor2 = resultado.text.toString().toDouble() //Obtiene la ultima entrada

            if(operacion != Operaciones.NING) {
                var res = obtenerResultado(
                    operacion,
                    valor1,
                    valor2
                )
                resultado.text =
                    formatearDouble(res) //Realiza la operación
                hist.add(Operation(valor1, valor2, operacion, res))
            }
            operacion = Operaciones.NING    //Reinicia la operación
        }

        ac.setOnClickListener {//Limpia la pantalla, valores y operaciones
            valor1 = 0.0
            valor2 = 0.0
            resultado.text = "0"
            operacion = Operaciones.NING
        }

        punto.setOnClickListener {//Agrega un punto siempre que no haya otro antes
            if(!resultado.text.toString().contains('.')) {
                resultado.text = resultado.text.toString() + "."
            }
        }

        resultado.setOnClickListener {//Esta función copia el valor del display actual
            val clipdata = ClipData.newPlainText("Resultado copiado", resultado.text.toString())
            clipboard.setPrimaryClip(clipdata)
        }
        potencia.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.POTENCIA
                valor1 = resultado.text.toString().toDouble()
                resultado.text = "0"
            }else{
                calcularOperacion(Operaciones.POTENCIA)
            }
        }
        raiz.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.RAIZ
                valor1 = resultado.text.toString().toDouble()
            }else{
                calcularOperacion(Operaciones.RAIZ)
            }
        }
        logaritmo.setOnClickListener {
            if (operacion == Operaciones.NING) {
                operacion = Operaciones.LOGARITMO
                valor1 = resultado.text.toString().toDouble()
            }else{
                calcularOperacion(Operaciones.LOGARITMO)
            }
        }
    }

    private fun formatearDouble(numero: Double): String {
        if(numero.isNaN() || numero.isInfinite()) return "Syntax error"
        val bd = BigDecimal(numero).setScale(10, RoundingMode.HALF_UP)
        return bd.stripTrailingZeros().toPlainString()
    }


    private fun obtenerResultado(operacion: Operaciones, val1: Double, val2: Double = 0.0): Double{
        /*
        * Recupera el resultado según la opración seleccionada, llamando a las funciones especificas
        * El segundo valor esta predeterminado, ya que existe la función masmenos que no necesita
        * el val2.
        * */

        return when(operacion){
            Operaciones.MODULO -> calModulo(val1, val2)
            Operaciones.MAS -> calSuma(val1, val2)
            Operaciones.MENOS -> calResta(val1, val2)
            Operaciones.DIVISION -> calDiv(val1, val2)
            Operaciones.MULTIPLICACION -> calMul(val1, val2)
            Operaciones.MASMENOS -> calMasMenos(val1)
            Operaciones.POTENCIA -> calPotencia(val1,val2)
            Operaciones.LOGARITMO -> calLog(val1)
            Operaciones.RAIZ-> calRaiz(val1)
            Operaciones.NING -> 0.0
        }
    }
    private fun calPotencia(val1: Double, val2: Double): Double {
        //Calcula la potencia

        return Math.pow(val1,val2)
    }
    private fun calRaiz(val1: Double): Double {
        //Calcula la Raiz

        return sqrt(val1)
    }
    private fun calLog(val1: Double): Double {
        //Calcula la logaritmo
        return log10(val1)
    }
    private fun calModulo(val1: Double, val2: Double): Double {
        //Calcula el modulo
        return val1 % val2
    }

    private fun calSuma(val1: Double, val2: Double): Double {
        //Calcula la suma
        return val1 + val2
    }

    private fun calResta(val1: Double, val2: Double): Double{
        //Calcula la resta
        return val1-val2
    }

    private fun calMul(val1: Double, val2: Double): Double{
        //Calcula la multiplicación
        return val1*val2
    }

    private fun calDiv(val1: Double, val2: Double): Double{
        //Calcula la división
        return val1/val2
    }

    private fun calMasMenos(val1: Double): Double{
        //Calcula el valor multiplicado por menos uno
        return -1 * val1
    }

    private fun habilitarNumero(numero:Button, valor:Number, resultado:Button){
        //Asigna las acciones para los botones de numeros
        numero.setOnClickListener {
            if(resultado.text.toString() == "0" || resultado.text.toString() == "0.0" || (!resultado.text.toString().isDigitsOnly() && !resultado.text.toString().contains('.')) ){
                resultado.text = valor.toString()
            }else if(resultado.text.toString() == "-0" || resultado.text.toString() == "-0.0"){
                resultado.text = "-" + valor.toString()
            }else{
                resultado.text = resultado.text.toString() + valor.toString()
            }
        }
    }
}