document.addEventListener('DOMContentLoaded', function() {
    getAllClients();
    document.getElementById('createForm').addEventListener('submit', createClient);
    document.getElementById('updateForm').addEventListener('submit', updateClient);
    document.getElementById('searchButton').addEventListener('click', searchClients);
    document.getElementById('searchInput').addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchClients();
        }
    });
});

function getAllClients() {
    fetch('http://localhost:8080/GTAL/api/clients')
        .then(response => response.json())
        .then(data => {
            displayClients(data);
        })
        .catch(error => console.error('Erreur:', error));
}

function searchClients() {
    const searchValue = document.getElementById('searchInput').value;
    if (searchValue.trim() === '') {
        getAllClients();
        return;
    }
    fetch(`http://localhost:8080/GTAL/api/clients/search/${encodeURIComponent(searchValue)}`)
        .then(response => response.json())
        .then(data => {
            displayClients(data);
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la recherche');
        });
}

function createClient(event) {
    event.preventDefault();
    const clientData = {
        numtel: document.getElementById('createNumtel').value,
        nom: document.getElementById('createNom').value,
        sexe: document.getElementById('createSexe').value,
        pays: document.getElementById('createPays').value,
        solde: parseInt(document.getElementById('createSolde').value),
        mail: document.getElementById('createMail').value
    };
    fetch('http://localhost:8080/GTAL/api/clients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(clientData)
    })
    .then(response => {
        if (response.status === 201) {
            alert('Client créé avec succès');
            document.getElementById('createForm').reset();
            getAllClients();
            showInsertForm(); // This ensures the insert form is visible after creation
        }
    })
    .catch(error => console.error('Erreur:', error));
}

function updateClient(event) {
    event.preventDefault();
    const numtel = document.getElementById('updateNumtel').value;
    const clientData = {
        numtel: numtel,
        nom: document.getElementById('updateNom').value,
        sexe: document.getElementById('updateSexe').value,
        pays: document.getElementById('updatePays').value,
        solde: parseInt(document.getElementById('updateSolde').value),
        mail: document.getElementById('updateMail').value
    };
    if (confirm("Êtes-vous sûr de vouloir mettre à jour ce client ?")) {
        fetch(`http://localhost:8080/GTAL/api/clients/${encodeURIComponent(numtel)}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(clientData)
        })
        .then(response => {
        if (response.ok) {
            alert('Client mis à jour avec succès');
            document.getElementById('updateForm').reset();
            getAllClients();
            showInsertForm()} else {
                throw new Error(`Erreur lors de la mise à jour: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la mise à jour du client');
        });
    }
}

function deleteClient(numtel) {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce client ?")) {
        fetch(`http://localhost:8080/GTAL/api/clients/${encodeURIComponent(numtel)}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Client supprimé avec succès');
                getAllClients();
            } else {
                throw new Error(`Erreur lors de la suppression: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du client');
        });
    }
}

function displayClients(clients) {
    const tableBody = document.getElementById('clientTableBody');
    tableBody.innerHTML = '';
    clients.forEach(client => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = client.numtel;
        row.insertCell(1).textContent = client.nom;
        row.insertCell(2).textContent = client.sexe;
        row.insertCell(3).textContent = client.pays;
        row.insertCell(4).textContent = client.solde;
        row.insertCell(5).textContent = client.mail;
        const actionCell = row.insertCell(6);
        
        const modifyButton = document.createElement('button');
		modifyButton.textContent = 'Modifier';
		modifyButton.className = 'btn btn-sm btn-primary me-2';
		modifyButton.addEventListener('click', () => showUpdateForm(client.numtel, client.nom, client.sexe, client.pays, client.solde, client.mail));

		const deleteButton = document.createElement('button');
		deleteButton.textContent = 'Supprimer';
		deleteButton.className = 'btn btn-sm btn-danger';
		deleteButton.addEventListener('click', () => deleteClient(client.numtel));	
        
        actionCell.appendChild(modifyButton);
        actionCell.appendChild(deleteButton);
    });
}

function showUpdateForm(numtel, nom, sexe, pays, solde, mail) {
    toggleForms(true);
    document.getElementById('updateNumtel').value = numtel;
    document.getElementById('updateNom').value = nom;
    document.getElementById('updateSexe').value = sexe;
    document.getElementById('updatePays').value = pays;
    document.getElementById('updateSolde').value = solde;
    document.getElementById('updateMail').value = mail;
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

