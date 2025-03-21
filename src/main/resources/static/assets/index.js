function actualizarLista(elementos, name) {
    const nameParts = name.split(".",2);
    const list = document.getElementById("list");
    while (list.firstChild) {
        list.removeChild(list.firstChild);
    }
    elementos.forEach((file, idx) => {
        let filename = "";
        if(nameParts.length ==2 ) {
            filename = nameParts[0] + idx + "." + nameParts[1];
        } else {
            filename = name + idx;
        }
        const li = document.createElement("li");
        const a = document.createElement("a");
        a.textContent = filename;
        a.href = `/api/getFile?filename=${file}&name=${filename}`;
        a.target = "_blank";
        li.appendChild(a);
        list.appendChild(li);
        list.appendChild(li);
    });
};

document.getElementById("subir").addEventListener("click", function(event) {
    event.preventDefault();
    const size = document.getElementById("size").value;
    const archivo = document.getElementById("archivo").files[0];
    const statusMessage = document.getElementById('statusMessage');

    if (!archivo) {
        statusMessage.textContent = "Faltan adjuntar archivo";
        statusMessage.className = "error";
        throw new Error("Faltan adjuntar archivo");
    }

    if (!size) {
        statusMessage.textContent = "Faltan indicar tamaño";
        statusMessage.className = "error";
        throw new Error("Faltan indicar tamaño");
    }

    if (archivo.size < size) {
        statusMessage.textContent = "El tamaño excede el del archivo";
        statusMessage.className = "error";
        throw new Error("El tamaño excede el del archivo");
    }

    const formData = new FormData();
    formData.append("file", archivo);
    statusMessage.textContent = "Procesando Archivo...";
    statusMessage.className = "info";
    fetch(`/api/upload?size=${size}`, {
        method: "POST",
        body: formData,
    })
        .then(res => {
            if(res.status == 413) {
                alert("Tamaño del archivo demasiado grande");
                throw new Error("Tamaño archivo excede el límite");
                statusMessage.textContent = "Tamaño archivo excede el límite";
                statusMessage.className = "error";
            }
            statusMessage.textContent = "Archivo procesado exitosamente.";
            statusMessage.className = "success";
            return res.json()
        })
        .catch(res => {
            console.error(res)
            statusMessage.textContent = "Hubo un error al procesar el archivo.";
            statusMessage.className = "error";
        })
        .then(res => {
            if(!res) {
                throw new Error("Sin respuesta del servidor");
                statusMessage.textContent = "Sin respuesta del servidor";
                statusMessage.className = "error";
            }
            actualizarLista(res, archivo.name)
        })
        .catch(error => {
            console.log("Error:", error)
            statusMessage.textContent = error;
            statusMessage.className = "error";
        });
});