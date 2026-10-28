package com.example.proyectoeva2appmoviles.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.proyectoeva2appmoviles.data.Aplicacion
import com.example.proyectoeva2appmoviles.data.BaseHelper
import com.example.proyectoeva2appmoviles.data.MemoriaAplicaciones
import com.example.proyectoeva2appmoviles.data.Usuario
import com.example.proyectoeva2appmoviles.data.instrumentoDisponible
import com.example.proyectoeva2appmoviles.data.preguntasHabitos
import com.example.proyectoeva2appmoviles.ui.theme.Blanco
import com.example.proyectoeva2appmoviles.ui.theme.Borde
import com.example.proyectoeva2appmoviles.ui.theme.Fondo
import com.example.proyectoeva2appmoviles.ui.theme.PillPendienteFondo
import com.example.proyectoeva2appmoviles.ui.theme.PillPendienteTexto
import com.example.proyectoeva2appmoviles.ui.theme.PillProcesoFondo
import com.example.proyectoeva2appmoviles.ui.theme.PillProcesoTexto
import com.example.proyectoeva2appmoviles.ui.theme.Texto
import com.example.proyectoeva2appmoviles.ui.theme.TextoSuave
import com.example.proyectoeva2appmoviles.ui.theme.Verde
import com.example.proyectoeva2appmoviles.ui.theme.VerdeSuave

private val FormaTarjeta = RoundedCornerShape(16.dp)
private val FormaBoton = RoundedCornerShape(14.dp)
private val cursos = listOf(
    "Primero Básico",
    "Segundo Básico",
    "Tercero Básico",
    "Cuarto Básico"
)

private sealed class Pantalla {
    data object Inicio : Pantalla()
    data object Login : Pantalla()
    data object Lista : Pantalla()
    data object Nueva : Pantalla()
    data class Detalle(val codigo: String) : Pantalla()
    data object Ingreso : Pantalla()
    data class Preguntas(val codigo: String) : Pantalla()
    data object Enviado : Pantalla()
}

@Composable
fun AppPsicoEvalua(base: BaseHelper) {
    val memoria = remember { MemoriaAplicaciones() }
    var sesion by remember { mutableStateOf<Usuario?>(null) }
    var pantalla by remember { mutableStateOf<Pantalla>(Pantalla.Inicio) }

    Scaffold(containerColor = Fondo) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (val actual = pantalla) {
                Pantalla.Inicio -> PantallaInicio(
                    onPsicologo = { pantalla = Pantalla.Login },
                    onEstudiante = { pantalla = Pantalla.Ingreso }
                )
                Pantalla.Login -> PantallaLogin(
                    base = base,
                    onEntrar = {
                        sesion = it
                        pantalla = Pantalla.Lista
                    },
                    onVolver = { pantalla = Pantalla.Inicio }
                )
                Pantalla.Lista -> PantallaLista(
                    base = memoria,
                    usuario = sesion?.usuario ?: "",
                    onNueva = { pantalla = Pantalla.Nueva },
                    onAbrir = { pantalla = Pantalla.Detalle(it) },
                    onVolver = {
                        sesion = null
                        pantalla = Pantalla.Inicio
                    }
                )
                Pantalla.Nueva -> PantallaNueva(
                    base = memoria,
                    onVolver = { pantalla = Pantalla.Lista }
                )
                is Pantalla.Detalle -> PantallaDetalle(
                    base = memoria,
                    codigo = actual.codigo,
                    usuario = sesion?.usuario ?: "",
                    onVolver = { pantalla = Pantalla.Lista }
                )
                Pantalla.Ingreso -> PantallaIngreso(
                    base = memoria,
                    onEntrar = { pantalla = Pantalla.Preguntas(it) },
                    onVolver = { pantalla = Pantalla.Inicio }
                )
                is Pantalla.Preguntas -> PantallaPreguntas(
                    base = memoria,
                    codigo = actual.codigo,
                    onEnviado = { pantalla = Pantalla.Enviado }
                )
                Pantalla.Enviado -> PantallaEnviado(
                    onVolver = { pantalla = Pantalla.Inicio }
                )
            }
        }
    }
}

@Composable
private fun Marca() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Verde),
            contentAlignment = Alignment.Center
        ) {
            Text("M", color = Blanco, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text("MPAZ PsicoEvalúa", color = Texto, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
    }
}

@Composable
private fun Barra(titulo: String, onVolver: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(
            "←",
            color = Texto,
            fontSize = 22.sp,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onVolver)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )
        Text(
            titulo,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = Texto,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Composable
private fun Etiqueta(texto: String) {
    Text(
        texto.uppercase(),
        color = TextoSuave,
        fontSize = 12.sp,
        letterSpacing = 1.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun BotonVerde(texto: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = FormaBoton,
        colors = ButtonDefaults.buttonColors(containerColor = Verde, contentColor = Blanco)
    ) {
        Text(texto, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun BotonBlanco(texto: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(FormaBoton)
            .border(1.dp, Borde, FormaBoton)
            .background(Blanco)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(texto, color = Texto, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun Pastilla(estado: String) {
    val (fondo, tinta) = when (estado) {
        "pendiente" -> PillPendienteFondo to PillPendienteTexto
        "en desarrollo" -> PillProcesoFondo to PillProcesoTexto
        else -> VerdeSuave to Verde
    }
    val nombre = when (estado) {
        "pendiente" -> "Pendiente"
        "en desarrollo" -> "En desarrollo"
        "finalizada" -> "Finalizada"
        else -> "Revisada"
    }
    Text(
        nombre,
        color = tinta,
        fontSize = 12.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(fondo)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
private fun PantallaInicio(onPsicologo: () -> Unit, onEstudiante: () -> Unit) {
    Marca()
    Spacer(modifier = Modifier.height(18.dp))
    Etiqueta("Bienvenida y bienvenido")
    Text("¿Cómo quieres ingresar?", color = Texto, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
    Text("Selecciona una opción para comenzar la demostración.", color = TextoSuave)
    TarjetaOpcion("Soy psicólogo", "Revisar aplicaciones", onPsicologo)
    TarjetaOpcion("Soy estudiante", "Ingresar con un código", onEstudiante)
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        "Esta demostración no solicita datos personales.",
        color = TextoSuave,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun PantallaLogin(
    base: BaseHelper,
    onEntrar: (Usuario) -> Unit,
    onVolver: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var aviso by remember { mutableStateOf("") }

    Barra("Ingreso del psicólogo", onVolver)
    Etiqueta("Cuenta profesional")
    Text("Inicia sesión", color = Texto, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    Text("La base solo guarda el id, el usuario y la contraseña.", color = TextoSuave)
    OutlinedTextField(
        value = usuario,
        onValueChange = { usuario = it },
        label = { Text("Usuario") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = FormaTarjeta
    )
    OutlinedTextField(
        value = contrasena,
        onValueChange = { contrasena = it },
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        shape = FormaTarjeta
    )
    BotonVerde("Entrar") {
        val encontrado = base.ingresar(usuario, contrasena)
        if (encontrado == null) {
            aviso = "Usuario o contraseña incorrectos."
        } else {
            onEntrar(encontrado)
        }
    }
    if (aviso.isNotEmpty()) Text(aviso, color = Texto)
    Text("Cuenta de prueba: psicologo / 1234", color = TextoSuave, fontSize = 13.sp)
}

@Composable
private fun TarjetaOpcion(titulo: String, detalle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FormaTarjeta)
            .border(1.dp, Borde, FormaTarjeta)
            .background(Blanco)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(titulo, color = Texto, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(detalle, color = TextoSuave, fontSize = 13.sp)
        }
        Text("›", color = TextoSuave, fontSize = 22.sp)
    }
}

@Composable
private fun PantallaLista(
    base: MemoriaAplicaciones,
    usuario: String,
    onNueva: () -> Unit,
    onAbrir: (String) -> Unit,
    onVolver: () -> Unit
) {
    val lista = base.listar()
    Barra("Aplicaciones", onVolver)
    Etiqueta("Psicólogo PIE · $usuario")
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text("Aplicaciones recientes", color = Texto, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(
            lista.size.toString(),
            color = Verde,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(CircleShape)
                .background(VerdeSuave)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
    BotonVerde("+   Nueva aplicación", onClick = onNueva)
    if (lista.isEmpty()) {
        Text("Todavía no hay aplicaciones.", color = TextoSuave)
    }
    lista.forEach { aplicacion ->
        FilaAplicacion(aplicacion, onClick = { onAbrir(aplicacion.codigo) })
    }
}

@Composable
private fun FilaAplicacion(aplicacion: Aplicacion, onClick: () -> Unit) {
    val corto = aplicacion.instrumento.removeSuffix(" (simulado)")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FormaTarjeta)
            .border(1.dp, Borde, FormaTarjeta)
            .background(Blanco)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(VerdeSuave),
            contentAlignment = Alignment.Center
        ) {
            Text(aplicacion.estudiante.take(3), color = Verde, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(aplicacion.estudiante, color = Texto, fontWeight = FontWeight.SemiBold)
            Text(corto, color = TextoSuave, fontSize = 13.sp)
        }
        Pastilla(aplicacion.estado)
        Spacer(modifier = Modifier.width(6.dp))
        Text("›", color = TextoSuave, fontSize = 20.sp)
    }
}

@Composable
private fun PantallaNueva(base: MemoriaAplicaciones, onVolver: () -> Unit) {
    var estudiante by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf(cursos.first()) }
    var codigo by remember { mutableStateOf<String?>(null) }
    var aviso by remember { mutableStateOf("") }

    Barra("Nueva aplicación", onVolver)
    Etiqueta("Aplicación simulada")
    Text("Configura la actividad", color = Texto, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    Text("Completa los datos ficticios antes de generar el código.", color = TextoSuave)
    CajaDato("Instrumento", instrumentoDisponible)
    Text("Prueba psicométrica: pendiente de autorización. Sin preguntas.", color = TextoSuave, fontSize = 13.sp)
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SelectorCurso(
            curso = curso,
            onElegir = { curso = it },
            modifier = Modifier.weight(1f)
        )
        Campo(
            "Identificador",
            estudiante,
            { estudiante = it },
            Modifier.weight(1f)
        )
    }
    if (codigo == null) {
        BotonVerde(
            "Generar código",
            onClick = {
                if (estudiante.isBlank()) {
                    aviso = "Escribe el identificador."
                    return@BotonVerde
                }
                val nuevo = base.crear(estudiante.trim(), curso.trim())
                if (nuevo == null) {
                    aviso = "No se pudo crear la aplicación."
                } else {
                    codigo = nuevo
                    aviso = ""
                }
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(FormaTarjeta)
                .background(VerdeSuave)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Código temporal generado", color = TextoSuave)
            Text(codigo!!, color = Texto, fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text("Compártelo solo con el estudiante.", color = TextoSuave, fontSize = 13.sp)
        }
        BotonBlanco("Volver a aplicaciones", onVolver)
    }
    if (aviso.isNotEmpty()) Text(aviso, color = Texto)
}

@Composable
private fun CajaDato(titulo: String, valor: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FormaTarjeta)
            .border(1.dp, Borde, FormaTarjeta)
            .background(Blanco)
            .padding(14.dp)
    ) {
        Text(titulo, color = TextoSuave, fontSize = 12.sp)
        Text(valor, color = Texto, fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorCurso(curso: String, onElegir: (String) -> Unit, modifier: Modifier) {
    var abierto by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = abierto,
        onExpandedChange = { abierto = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = curso,
            onValueChange = {},
            readOnly = true,
            label = { Text("Curso") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = abierto) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            shape = FormaTarjeta
        )
        ExposedDropdownMenu(
            expanded = abierto,
            onDismissRequest = { abierto = false }
        ) {
            cursos.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onElegir(opcion)
                        abierto = false
                    }
                )
            }
        }
    }
}

@Composable
private fun Campo(titulo: String, valor: String, onChange: (String) -> Unit, modifier: Modifier) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(titulo) },
        modifier = modifier,
        singleLine = true,
        shape = FormaTarjeta
    )
}

@Composable
private fun PantallaDetalle(
    base: MemoriaAplicaciones,
    codigo: String,
    usuario: String,
    onVolver: () -> Unit
) {
    var aplicacion by remember { mutableStateOf(base.buscar(codigo)) }
    var observacion by remember(aplicacion?.codigo) { mutableStateOf(aplicacion?.observacion ?: "") }
    var aviso by remember { mutableStateOf("") }
    val actual = aplicacion

    Barra("Detalle", onVolver)
    if (actual == null) {
        Text("No se encontró esa aplicación.", color = TextoSuave)
    } else {
        CajaDato("Usuario responsable", usuario)
        CajaDato("Estudiante", "${actual.estudiante} · ${actual.curso}")
        CajaDato("Instrumento", actual.instrumento)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Código ${actual.codigo}", color = Texto, fontWeight = FontWeight.Medium)
            Pastilla(actual.estado)
        }
        if (actual.fechaInicio.isNotBlank()) CajaDato("Inicio", actual.fechaInicio)
        if (actual.fechaTermino.isNotBlank()) CajaDato("Término", actual.fechaTermino)
        when (actual.estado) {
            "pendiente" -> Text("El estudiante todavía no empieza.", color = TextoSuave)
            "en desarrollo" -> Text("El estudiante está respondiendo.", color = TextoSuave)
            else -> {
                CajaDato("Respuestas", if (actual.respuestas.isBlank()) "Sin respuestas." else actual.respuestas)
                Text(
                    "Puntaje de prueba: ${actual.puntaje}. No es un diagnóstico.",
                    color = Texto,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        if (actual.estado == "finalizada") {
            OutlinedTextField(
                value = observacion,
                onValueChange = { observacion = it },
                label = { Text("Observación") },
                modifier = Modifier.fillMaxWidth(),
                shape = FormaTarjeta
            )
            BotonVerde("Guardar") {
                if (observacion.isBlank()) {
                    aviso = "Escribe una observación."
                    return@BotonVerde
                }
                val ok = base.guardarObservacion(actual.codigo, observacion.trim())
                if (ok) {
                    aplicacion = base.buscar(actual.codigo)
                    aviso = "Quedó revisada."
                } else {
                    aviso = "No se pudo guardar."
                }
            }
        }
        if (actual.estado == "revisada" && actual.observacion.isNotBlank()) {
            CajaDato("Observación", actual.observacion)
        }
    }
    if (aviso.isNotEmpty()) Text(aviso, color = Verde, fontWeight = FontWeight.Medium)
}

@Composable
private fun PantallaIngreso(
    base: MemoriaAplicaciones,
    onEntrar: (String) -> Unit,
    onVolver: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }
    var aviso by remember { mutableStateOf("") }

    Barra("Ingreso", onVolver)
    Etiqueta("Estudiante")
    Text("Ingresa con un código", color = Texto, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    Text("Escribe el código que te entregó el psicólogo.", color = TextoSuave)
    OutlinedTextField(
        value = codigo,
        onValueChange = { codigo = it },
        label = { Text("Código") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = FormaTarjeta
    )
    BotonVerde("Entrar") {
        val encontrada = base.buscar(codigo)
        when {
            encontrada == null -> aviso = "Ese código no existe."
            encontrada.estado == "finalizada" || encontrada.estado == "revisada" ->
                aviso = "Este código ya fue respondido."
            encontrada.estado == "pendiente" || encontrada.estado == "en desarrollo" ->
                onEntrar(encontrada.codigo)
        }
    }
    if (aviso.isNotEmpty()) Text(aviso, color = Texto)
}

@Composable
private fun PantallaPreguntas(base: MemoriaAplicaciones, codigo: String, onEnviado: () -> Unit) {
    var indice by remember { mutableIntStateOf(0) }
    var puntos by remember { mutableIntStateOf(0) }
    var respuestas by remember { mutableStateOf(listOf<String>()) }
    val pregunta = preguntasHabitos[indice]

    LaunchedEffect(codigo) {
        val actual = base.buscar(codigo)
        if (actual?.estado == "pendiente") {
            base.empezar(codigo)
        }
    }

    Etiqueta("Hábitos de estudio")
    Text("Pregunta ${indice + 1} de ${preguntasHabitos.size}", color = TextoSuave)
    Text(pregunta.texto, color = Texto, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)
    if (indice == 0) {
        Text("Responde según lo que haces en clases. No hay respuestas buenas ni malas.", color = TextoSuave)
    }
    pregunta.opciones.forEach { opcion ->
        TarjetaOpcion(opcion.texto, "") {
            val nuevas = respuestas + "${indice + 1}. ${opcion.texto}"
            val total = puntos + opcion.puntos
            if (indice == preguntasHabitos.lastIndex) {
                base.enviar(codigo, nuevas.joinToString("\n"), total.toString())
                onEnviado()
            } else {
                respuestas = nuevas
                puntos = total
                indice += 1
            }
        }
    }
}

@Composable
private fun PantallaEnviado(onVolver: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(VerdeSuave),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", color = Verde, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Etiqueta("Actividad completada")
        Text("Respuestas enviadas", color = Texto, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(
            "Tus respuestas fueron guardadas. Ya puedes devolver el teléfono a la persona a cargo.",
            color = TextoSuave,
            textAlign = TextAlign.Center
        )
        Text(
            "Esta pantalla no muestra puntaje, resultado ni diagnóstico.",
            color = TextoSuave,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(FormaTarjeta)
                .border(1.dp, Borde, FormaTarjeta)
                .background(Blanco)
                .padding(16.dp)
        )
        BotonBlanco("Volver al inicio", onVolver)
    }
}
