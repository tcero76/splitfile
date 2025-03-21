function updateStatus(msg, tipo) {
    const statusMessage = document.getElementById('statusMessage');
    statusMessage.textContent = msg;
    statusMessage.className = tipo;
    if (tipo === "error") throw new Error(msg);
}

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
    if (!archivo) updateStatus("Faltan adjuntar archivo", "error");
    if (!size) updateStatus("Faltan indicar tamaño","error")
    if (archivo.size < size) updateStatus("El tamaño excede el del archivo", "error")
    const formData = new FormData();
    formData.append("file", archivo);
    updateStatus("Procesando Archivo...", "info")
    fetch(`/api/upload?size=${size}`, {
            method: "POST",
            body: formData,
        })
    .then(res => {
        if(res.status == 413) {
            updateStatus("Tamaño archivo excede el límite", "error")
        }
        updateStatus("Archivo procesado exitosamente", "success")
        return res.json()
    })
    .catch(res => updateStatus("Hubo un error al procesar el archivo", "error"))
    .then(res => {
        if(!res) updateStatus("Sin respuesta del servidor", "error")
        actualizarLista(res, archivo.name)
    })
    .catch(err => updateStatus(err, "error"));
});