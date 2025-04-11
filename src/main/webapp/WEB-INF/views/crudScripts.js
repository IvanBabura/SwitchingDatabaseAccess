const radBtn = document.querySelectorAll("input[type='radio']");
radBtn.forEach(button => {
    button.onclick = () => {
        if (button.checked) {
            let checkbox_remove_results = document.querySelector('#checkbox_remove_results');
            let all_results_text = document.querySelectorAll('[class^="result"]');
            let selected_rbt_result = document.querySelector('.selected_rbt_result');
            let xhr = new XMLHttpRequest();
            let url = "/sdba/type";
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/text");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && (xhr.status === 200)) {
                    selected_rbt_result.innerHTML = this.responseText;
                    console.log("Change to " + this.responseText);
                    if (checkbox_remove_results.checked === true){
                        for(let el of all_results_text){
                            el.innerHTML = "...";
                        }
                    }
                } else {
                    selected_rbt_result.innerHTML = "Some error";
                }
            };
            console.log("Send to change type.");
            xhr.send(button.value);
        }
    }
});

function findById() {
    let person_id = document.querySelector('#person_id_findById');
    let result = document.querySelector('.result_findById');
    if (person_id.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba?id=" + person_id.value;
        xhr.open("GET", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 200)) {
                result.innerHTML = this.responseText;
                console.log("Found: " + this.responseText);
            } else {
                result.innerHTML = "some error";
                console.log(this.responseText);
            }
        };
        let data = JSON.stringify(
            {
                "id": person_id.value
            }
        );
        console.log("Send to find: " + data);
        xhr.send(data);
    } else {
        result.innerHTML = "the value must not be null";
    }
}

function saveByName() {
    let person_name = document.querySelector('#person_name_saveByName');
    let result = document.querySelector('.result_saveByName');
    if (person_name.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba";
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/text");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 201 || xhr.status === 200)) {
                //result.innerHTML = this.responseText;
                result.innerHTML = "saved '" + person_name.value + "'";
                console.log("saved: " + person_name.value);
            } else {
                console.log(this.responseText);
                result.innerHTML = "some error";
            }
        };
        console.log("Send to save: " + person_name.value);
        xhr.send(person_name.value);
    } else {
        result.innerHTML = "the value must not be null";
    }
}

function update() {
    let person_id = document.querySelector('#person_id_update');
    let person_name = document.querySelector('#person_name_update');
    let result = document.querySelector('.result_update');
    if (person_name.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba";
        xhr.open("PUT", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 200)) {
                result.innerHTML = this.responseText + ": {" + person_id.value + " ; " + person_name.value + "}";
                console.log("Updated: " + data);
            } else {
                console.log(this.responseText);
                result.innerHTML = "some error";
            }
        };
        let data = JSON.stringify(
            {
                "id": person_id.value,
                "name": person_name.value
            }
        );
        console.log("Send to update: " + data);
        xhr.send(data);
    } else {
        result.innerHTML = "the value must not be null";
    }
}

function deleteById() {
    let person_id = document.querySelector('#person_id_delete');
    let result = document.querySelector('.result_delete');
    if (person_id.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba?id=" + person_id.value;
        xhr.open("DELETE", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 200)) {
                result.innerHTML = this.responseText;
                console.log("Deleted: " + data);
            } else {
                result.innerHTML = "some error";
                console.log(this.responseText);
            }
        };
        let data = JSON.stringify(
            {
                "id": person_id.value
            }
        );
        console.log("Send to delete: " + data);
        xhr.send(data);
    } else {
        result.innerHTML = "the value must not be null";
    }
}

function getAll() {
    let result = document.querySelector('.result_getAll');
    let xhr = new XMLHttpRequest();
    let url = "/sdba/all";
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && (xhr.status === 200)) {
            result.innerHTML = this.responseText;
            console.log("got: " + this.responseText);
        } else {
            result.innerHTML = "some error";
            console.log(this.responseText);
        }
    };
    console.log("Send to get all.");
    xhr.send();
}

function getAllLikeTemplate() {
    let startOfName = document.querySelector('#person_startOfName');
    let result = document.querySelector('.result_startOfName');
    if (startOfName.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba/like?startOfName="+startOfName.value;
        xhr.open("GET", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 201 || xhr.status === 200)) {
                result.innerHTML = this.responseText;
                console.log("got: " + this.responseText);
            } else {
                console.log(this.responseText);
                result.innerHTML = "some error";
            }
        };
        let data = JSON.stringify(
            {
                "startOfName": startOfName.value
            }
        );
        console.log("Send to find like: " + data);
        xhr.send(data);
    } else {
        result.innerHTML = "the value must not be null";
    }
}

function getAllNamesStartsWith() {
    let startOfName = document.querySelector('#person_startOfNameOnly');
    let result = document.querySelector('.result_startOfNameOnly');
    if (startOfName.value !== "") {
        let xhr = new XMLHttpRequest();
        let url = "/sdba/likeNameOnly?startOfName="+startOfName.value;
        xhr.open("GET", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 201 || xhr.status === 200)) {
                result.innerHTML = this.responseText;
                console.log("got: " + this.responseText);
            } else {
                console.log(this.responseText);
                result.innerHTML = "some error";
            }
        };
        let data = JSON.stringify(
            {
                "startOfName": startOfName.value
            }
        );
        console.log("Send to find like: " + data);
        xhr.send(data);
    } else {
        result.innerHTML = "the value must not be null";
    }
}