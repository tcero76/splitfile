# Instrucciones


## Para Windows

### Paso 1: Descargar Java

Si la versión de Windows posee curl, es posible descargar los binarios de Java ejecutando el siguiente comando:

```cmd
curl -L -H "Cookie: oraclelicense=accept-securebackup-cookie" -o jdk-21.0.5_windows-x64_bin.zip "https://download.oracle.com/java/21/archive/jdk-21.0.5_windows-x64_bin.zip"
```

En caso contrario, es posible descargarlo en el siguiente enlace, buscando el archivo `jdk-21.0.5_windows-x64_bin.zip`:

[Descargar Java](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

### Paso 2: Crear carpeta Java
Ahora, necesitamos crear una carpeta en c:\java. Se puede crear ejecutando el siguiente comando:

```cmd
mkdir c:\java
```

### Paso 3: Descomprimir binarios de Java

Para descomprimir los binarios en la carpeta recién creada se debe ejecutar el siguiente comando:

```cmd
tar -xf jdk-21.0.5_windows-x64_bin.zip -C "C:\java"
```

### Paso 4: Descargar .jar con el bitcode del proyecto
Se debe descargar el ejecutable del proyecto a través del siguiente comando:
```cmd
curl -L -o splitfile-0.0.1-SNAPSHOT.jar "https://github.com/tcero76/splitfile/releases/download/v0.0.1/splitfile-0.0.1-SNAPSHOT.jar"
```

### Paso 5: Ejecutar el proyecto
Para ejecutar el proyecto se debe usar el siguiente comando:
```cmd
C:\java\jdk-21.0.5\bin\java -jar splitfile-0.0.1-SNAPSHOT.jar
```
Este generará una carpeta `upload\` en la carpeta temporales de SO. Si se quisiera utilizar otra ruta específica se debe indicar de la siguiente manera:

```cmd
C:\java\jdk-21.0.5\bin\java -Djava.io.tmpdir=C:\miTemporal -jar splitfile-0.0.1-SNAPSHOT.jar
```

## Para Linux
