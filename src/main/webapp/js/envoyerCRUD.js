function getAllEnvoyer() {
    return fetch('http://localhost:8080/GTAL/api/envoyer')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erreur:', error);
            throw error;
        });
}

function searchEnvoyerByDate(searchValue) {
    return fetch(`http://localhost:8080/GTAL/api/envoyer/search/${encodeURIComponent(searchValue)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la recherche');
            throw error;
        });
}

function createEnvoyer(envoyerData) {
    return fetch('http://localhost:8080/GTAL/api/envoyer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(envoyerData)
    })
    .then(response => {
        if (response.status === 201) {
            return { success: true, message: 'Envoi créé avec succès' };
        }
        throw new Error('Erreur lors de la création de l\'envoi');
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}

function updateEnvoyer(idEnv, envoyerData) {
    return fetch(`http://localhost:8080/GTAL/api/envoyer/${encodeURIComponent(idEnv)}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(envoyerData)
    })
    .then(response => {
        if (response.ok) {
            return { success: true, message: 'Envoi mis à jour avec succès' };
        }
        throw new Error(`Erreur lors de la mise à jour: ${response.status}`);
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}

function deleteEnvoyer(idEnv) {
    return fetch(`http://localhost:8080/GTAL/api/envoyer/${encodeURIComponent(idEnv)}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            return { success: true, message: 'Envoi supprimé avec succès' };
        }
        throw new Error(`Erreur lors de la suppression: ${response.status}`);
    })
    .catch(error => {
        console.error('Erreur:', error);
        return { success: false, message: error.message };
    });
}

function getAllPhoneNumbers() {
    return fetch('http://localhost:8080/GTAL/api/envoyer/phoneNumbers')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erreur lors du chargement des numéros de téléphone:', error);
            return [];
        });
}