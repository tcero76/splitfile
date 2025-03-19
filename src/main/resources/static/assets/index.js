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
    console.log(archivo)
    const formData = new FormData();
    formData.append("file", archivo);
    fetch(`/api/upload?size=${size}`, {
        method: "POST",
        body: formData,
    })
        .then(response => response.json())
        .catch(console.error)
        .then(res => actualizarLista(res, archivo.name))
        .catch(error => console.error("Error:", error));
});