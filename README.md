# MPAZ PsicoEvalúa

Aplicación Android para aplicar un cuestionario simulado dentro de una escuela. Los datos son ficticios. No diagnostica ni guarda información real de estudiantes.

Hay dos perfiles:

- **Psicólogo:** inicia sesión, elige uno de los cuatro cursos (1.º a 4.º básico), escribe un identificador ficticio y genera un código.
- **Estudiante:** entra con ese código, responde 4 preguntas y ve solo el mensaje “Respuestas enviadas”. No ve el puntaje.

El psicólogo puede revisar las respuestas, el puntaje de prueba y dejar una observación. El puntaje no es un diagnóstico.

## Cuenta de prueba

- Usuario: `psicologo`
- Contraseña: `1234`

## Base de datos

SQLite guarda solo la cuenta del psicólogo: id, usuario y contraseña. Las preguntas van escritas en la app. Las aplicaciones y las respuestas se mantienen mientras la app está abierta.

## Cómo abrirla

1. Clonar el repositorio.
2. Abrir la carpeta en Android Studio.
3. Ejecutarla en un celular o en el emulador.
