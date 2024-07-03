/**
 * 
 */
// fraisCRUD.js

function getAllFrais() {
    return fetch('http://localhost:8080/GTAL/api/frais')
        .then(response => response.json())
        .catch(error => console.error('Erreur:', error));
}

function createFrais(fraisData) {
    return fetch('http://localhost:8080/GTAL/api/frais', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(fraisData)
    })
    .then(response => {
        if (response.status === 201) {
            return { success: true, message: 'Frais créé avec succès' };
        }
        throw new Error('Erreur lors de la création du frais');
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}

function updateFrais(idfrais, fraisData) {
    return fetch(`http://localhost:8080/GTAL/api/frais/${encodeURIComponent(idfrais)}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(fraisData)
    })
    .then(response => {
        if (response.ok) {
            return { success: true, message: 'Frais mis à jour avec succès' };
        }
        throw new Error(`Erreur lors de la mise à jour: ${response.status}`);
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}

function deleteFrais(idfrais) {
    return fetch(`http://localhost:8080/GTAL/api/frais/${encodeURIComponent(idfrais)}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            return { success: true, message: 'Frais supprimé avec succès' };
        }
        throw new Error(`Erreur lors de la suppression: ${response.status}`);
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}