// frais.js

document.addEventListener('DOMContentLoaded', function() {
    refreshFraisList();
    document.getElementById('createForm').addEventListener('submit', handleCreateFrais);
    document.getElementById('updateForm').addEventListener('submit', handleUpdateFrais);
});

function refreshFraisList() {
    getAllFrais().then(data => {
        displayFrais(data);
    });
}

function handleCreateFrais(event) {
    event.preventDefault();
    const fraisData = {
        idfrais: document.getElementById('createIdfrais').value,
        montant1: document.getElementById('createMontant1').value,
        montant2: document.getElementById('createMontant2').value,
        frais: document.getElementById('createFrais').value,
    };
    createFrais(fraisData).then(result => {
        if (result.success) {
            alert(result.message);
            document.getElementById('createForm').reset();
            refreshFraisList();
        } else {
            alert(result.message);
        }
    });
}

function handleUpdateFrais(event) {
    event.preventDefault();
    const idfrais = document.getElementById('updateIdfrais').value;
    const fraisData = {
        idfrais: idfrais,
        montant1: document.getElementById('updateMontant1').value,
        montant2: document.getElementById('updateMontant2').value,
        frais: document.getElementById('updateFrais').value
    };
    if (confirm("Êtes-vous sûr de vouloir mettre à jour ce Frais ?")) {
        updateFrais(idfrais, fraisData).then(result => {
            if (result.success) {
                alert(result.message);
                document.getElementById('updateForm').reset();
                refreshFraisList();
                showInsertForm();
            } else {
                alert(result.message);
            }
        });
    }
}

function handleDeleteFrais(idfrais) {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce Frais ?")) {
        deleteFrais(idfrais).then(result => {
            if (result.success) {
                alert(result.message);
                refreshFraisList();
            } else {
                alert(result.message);
            }
        });
    }
}

function displayFrais(frais) {
    const tableBody = document.getElementById('fraisTableBody');
    tableBody.innerHTML = '';
    frais.forEach(frais => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = frais.idfrais;
        row.insertCell(1).textContent = frais.montant1;
        row.insertCell(2).textContent = frais.montant2;
        row.insertCell(3).textContent = frais.frais;
        const actionCell = row.insertCell(4);

        const modifyButton = document.createElement('button');
        modifyButton.textContent = 'Modifier';
        modifyButton.className = 'btn btn-sm btn-primary me-2';
        modifyButton.addEventListener('click', () => showUpdateForm(frais.idfrais, frais.montant1, frais.montant2, frais.frais));

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Supprimer';
        deleteButton.className = 'btn btn-sm btn-danger';
        deleteButton.addEventListener('click', () => handleDeleteFrais(frais.idfrais));

        actionCell.appendChild(modifyButton);
        actionCell.appendChild(deleteButton);
    });
}

function showUpdateForm(idfrais, montant1, montant2, frais) {
    toggleForms(true);
    document.getElementById('updateIdfrais').value = idfrais;
    document.getElementById('updateMontant1').value = montant1;
    document.getElementById('updateMontant2').value = montant2;
    document.getElementById('updateFrais').value = frais;
}

function showInsertForm() {
    toggleForms(false);
    document.getElementById('updateForm').reset();
}
function toggleForms(showUpdate = false) {
    const insertForm = document.getElementById('insertFormContainer');
    const updateForm = document.getElementById('updateFormContainer');
    
    if (showUpdate) {
        insertForm.classList.add('hidden');
        updateForm.classList.remove('hidden');
    } else {
        updateForm.classList.add('hidden');
        insertForm.classList.remove('hidden');
    }
}