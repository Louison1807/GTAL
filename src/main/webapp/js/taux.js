/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
    getAllTaux();
    document.getElementById('createForm').addEventListener('submit', createTaux);
  
});

function getAllTaux() {
    fetch('http://localhost:8080/GTAL/api/taux')
        .then(response => response.json())
        .then(data => {
            displayTaux(data);
        })
        .catch(error => console.error('Erreur:', error));
}


function createTaux(event) {
    event.preventDefault();
    const tauxData = {
        idtaux: document.getElementById('createIdtaux').value,
        montant1: parseInt(document.getElementById('createMontant1').value),
        montant2: parseInt(document.getElementById('createMontant2').value)
    };
    fetch('http://localhost:8080/GTAL/api/taux', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(tauxData)
    })
    .then(response => {
        if (response.status === 201) {
            alert('Taux créé avec succès');
            document.getElementById('createForm').reset();
            getAllTaux();
        }
    })
    .catch(error => console.error('Erreur:', error));
}

function updateTaux(event) {
    event.preventDefault();
    const idtaux = document.getElementById('updateIdtaux').value;
    const tauxData = {
        idtaux: idtaux,
        montant1: parseInt(document.getElementById('updateMontant1').value),
        montant2: parseInt(document.getElementById('updateMontant2').value)
    };
    if (confirm("Êtes-vous sûr de vouloir mettre à jour ce taux ?")) {
        fetch(`http://localhost:8080/GTAL/api/taux/${encodeURIComponent(idtaux)}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tauxData)
        })
        .then(response => {
            if (response.ok) {
                alert('Taux mis à jour avec succès');
                document.getElementById('updateForm').reset();
                getAllTaux();
                showInsertForm();
            } else {
                throw new Error(`Erreur lors de la mise à jour: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la mise à jour du taux');
        });
    }
}

function deleteTaux(idtaux) {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce taux ?")) {
        fetch(`http://localhost:8080/GTAL/api/taux/${encodeURIComponent(idtaux)}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Taux supprimé avec succès');
                getAllTaux();
            } else {
                throw new Error(`Erreur lors de la suppression: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du taux');
        });
    }
}

function displayTaux(tauxs) {
    const tableBody = document.getElementById('tauxTableBody');
    tableBody.innerHTML = '';
    tauxs.forEach(taux => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = taux.idtaux;
        row.insertCell(1).textContent = taux.montant1;
        row.insertCell(2).textContent = taux.montant2;
        const actionCell = row.insertCell(3);
        
        const modifyButton = document.createElement('button');
        modifyButton.textContent = 'Modifier';
        modifyButton.className = 'btn btn-sm btn-primary me-2';
        modifyButton.addEventListener('click', () => showUpdateForm(taux.idtaux, taux.montant1, taux.montant2));
        
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Supprimer';
        deleteButton.className = 'btn btn-sm btn-danger';
        deleteButton.addEventListener('click', () => deleteTaux(taux.idtaux));
        
        actionCell.appendChild(modifyButton);
        actionCell.appendChild(deleteButton);
    });
}

function showUpdateForm(idtaux, montant1, montant2) {
    toggleForms(true);
    document.getElementById('updateIdtaux').value = idtaux;
    document.getElementById('updateMontant1').value = montant1;
    document.getElementById('updateMontant2').value = montant2;
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
