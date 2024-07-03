document.addEventListener('DOMContentLoaded', function() {
    refreshEnvoyerList();
   
    document.getElementById('createForm').addEventListener('submit', handleCreateEnvoyer);
    document.getElementById('updateForm').addEventListener('submit', handleUpdateEnvoyer);
    document.getElementById('searchButton').addEventListener('click', handleSearch);
    loadPhoneNumbers();
    
    // Définir la date d'aujourd'hui comme valeur par défaut pour le champ de création
    setDefaultDate();
});

function setDefaultDate() {
    const today = new Date();
    const formattedDate = today.toISOString().split('T')[0]; // Format YYYY-MM-DD
    document.getElementById('createDate').value = formattedDate;
}

function refreshEnvoyerList() {
    getAllEnvoyer().then(data => {
        displayEnvoyer(data);
    });
}

function handleCreateEnvoyer(event) {
    event.preventDefault();
    const envoyerData = {
        idEnv: document.getElementById('createIdEnv').value,
        numEnvoyeur: document.getElementById('createNumEnvoyeur').value,
        numRecepteur: document.getElementById('createNumRecepteur').value,
        montant: parseInt(document.getElementById('createMontant').value),
        date: document.getElementById('createDate').value,
        raison: document.getElementById('createRaison').value,
    };
    createEnvoyer(envoyerData).then(result => {
        if (result.success) {
            alert(result.message);
            document.getElementById('createForm').reset();
            setDefaultDate(); // Réinitialiser la date par défaut
            refreshEnvoyerList();
        } else {
            alert(result.message);
        }
    });
}

function handleUpdateEnvoyer(event) {
    event.preventDefault();
    const idEnv = document.getElementById('updateIdEnv').value;
    const envoyerData = {
        idEnv: idEnv,
        numEnvoyeur: document.getElementById('updateNumEnvoyeur').value,
        numRecepteur: document.getElementById('updateNumRecepteur').value,
        montant: parseInt(document.getElementById('updateMontant').value),
        date: document.getElementById('updateDate').value,
        raison: document.getElementById('updateRaison').value,
    };
    console.log("Données à mettre à jour:", envoyerData);
    if (confirm("Êtes-vous sûr de vouloir mettre à jour cet envoi ?")) {
        updateEnvoyer(idEnv, envoyerData).then(result => {
            if (result.success) {
                alert(result.message);
                document.getElementById('updateForm').reset();
                refreshEnvoyerList();
                showInsertForm();
            } else {
                alert(result.message);
            }
        });
    }
}

function handleDeleteEnvoyer(idEnv) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cet envoi ?")) {
        deleteEnvoyer(idEnv).then(result => {
            if (result.success) {
                alert(result.message);
                refreshEnvoyerList();
            } else {
                alert(result.message);
            }
        });
    }
}

function handleSearch() {
    const searchValue = document.getElementById('searchInput').value;
    searchEnvoyerByDate(searchValue).then(data => {
        displayEnvoyer(data);
    });
}

function displayEnvoyer(envoyers) {
    console.log("displayEnvoyer appelé avec:", envoyers);
    const tableBody = document.getElementById('envoyerTableBody');
    tableBody.innerHTML = '';
    envoyers.forEach(envoyer => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = envoyer.idEnv;
        row.insertCell(1).textContent = envoyer.numEnvoyeur;
        row.insertCell(2).textContent = envoyer.numRecepteur;
        row.insertCell(3).textContent = envoyer.montant;
        
        row.insertCell(4).textContent = formatDate(envoyer.date);
        
        row.insertCell(5).textContent = envoyer.raison;
        const actionCell = row.insertCell(6);
        
        const modifyButton = document.createElement('button');
        modifyButton.textContent = 'Modifier';
        modifyButton.classList.add('btn', 'btn-warning', 'me-2');
        modifyButton.addEventListener('click', () => showUpdateForm(envoyer));
        
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Supprimer';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButton.addEventListener('click', () => handleDeleteEnvoyer(envoyer.idEnv));
        
        actionCell.appendChild(modifyButton);
        actionCell.appendChild(deleteButton);
    });
}

function formatDate(dateString) {
    if (!dateString) return '';

    let date;
    if (dateString.includes('T')) {
        // Format ISO
        date = new Date(dateString);
    } else if (dateString.includes('/')) {
        // Format DD/MM/YYYY
        const parts = dateString.split('/');
        date = new Date(`${parts[2]}-${parts[1]}-${parts[0]}`);
    } else if (dateString.includes('-')) {
        // Format YYYY-MM-DD
        date = new Date(dateString);
    } else {
        // Format non reconnu
        return 'Invalid date';
    }

    if (isNaN(date.getTime())) {
        return 'Invalid date';
    }

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
}

function showUpdateForm(envoyer) {
    console.log("showUpdateForm appelé avec:", envoyer);
    document.getElementById('insertFormContainer').classList.add('d-none');
    document.getElementById('updateFormContainer').classList.remove('d-none');
    document.getElementById('updateFormContainer').style.display = 'block';

    document.getElementById('updateIdEnv').value = envoyer.idEnv;
    document.getElementById('updateNumEnvoyeur').value = envoyer.numEnvoyeur;
    document.getElementById('updateNumRecepteur').value = envoyer.numRecepteur;
    document.getElementById('updateMontant').value = envoyer.montant;
    
    // Convertir la date au format YYYY-MM-DD pour l'input de type date
    const dateParts = envoyer.date.split('/');
    const formattedDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`;
    document.getElementById('updateDate').value = formattedDate;
    
    document.getElementById('updateRaison').value = envoyer.raison;
}

function showInsertForm() {
    document.getElementById('updateFormContainer').classList.add('d-none');
    document.getElementById('insertFormContainer').classList.remove('d-none');
    document.getElementById('insertFormContainer').style.display = 'block';
    
    // Réinitialiser la date par défaut lorsqu'on revient au formulaire d'insertion
    setDefaultDate();
}

function loadPhoneNumbers() {
    getAllPhoneNumbers().then(phoneNumbers => {
        const envoyeurSelects = document.querySelectorAll('#createNumEnvoyeur, #updateNumEnvoyeur');
        const recepteurSelects = document.querySelectorAll('#createNumRecepteur, #updateNumRecepteur');
        
        envoyeurSelects.forEach(select => populateSelect(select, phoneNumbers));
        recepteurSelects.forEach(select => populateSelect(select, phoneNumbers));
    });
}

function populateSelect(select, options) {
    select.innerHTML = '';
    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option;
        optionElement.textContent = option;
        select.appendChild(optionElement);
    });
}