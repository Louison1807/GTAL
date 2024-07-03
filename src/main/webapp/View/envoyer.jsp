<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Envois</title>
    <link rel="stylesheet" href="../bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    <script src="../js/envoyer.js"></script>
    <script src="../js/envoyerCRUD.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Gestion</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="Client.jsp">Client</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="frais.jsp">Frais</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="taux.jsp">Taux</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="envoyer.jsp">Envoyer</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="mb-4">Gestion des Envois</h1>

        <div id="insertFormContainer" class="mb-4">
            <h2>Créer un nouvel envoi</h2>
            <form id="createForm" class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" id="createIdEnv" placeholder="ID Envoi" required>
                </div>
                <div class="col-md-6">
                    <select class="form-select" id="createNumEnvoyeur" required></select>
                </div>
                <div class="col-md-6">
                    <select class="form-select" id="createNumRecepteur" required></select>
                </div>
                <div class="col-md-6">
                    <input type="number" class="form-control" id="createMontant" placeholder="Montant" required>
                </div>
                <div class="col-md-6">
                    <input type="date" class="form-control" id="createDate" required>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="createRaison" placeholder="Raison" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Créer</button>
                </div>
            </form>
        </div>

        <div id="updateFormContainer" class="mb-4 d-none">
            <h2>Mettre à jour un envoi</h2>
            <form id="updateForm" class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" id="updateIdEnv" placeholder="ID Envoi" readonly>
                </div>
                <div class="col-md-6">
                    <select class="form-select" id="updateNumEnvoyeur" required></select>
                </div>
                <div class="col-md-6">
                    <select class="form-select" id="updateNumRecepteur" required></select>
                </div>
                <div class="col-md-6">
                    <input type="number" class="form-control" id="updateMontant" placeholder="Montant" required>
                </div>
                <div class="col-md-6">
                    <input type="date" class="form-control" id="updateDate" required>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="updateRaison" placeholder="Raison" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                    <button type="button" class="btn btn-secondary" onclick="showInsertForm()">Annuler</button>
                </div>
            </form>
        </div>

        <div class="mb-4">
    		<div class="input-group">
        		<input type="date" class="form-control" id="searchInput" placeholder="Rechercher">
        		<button class="btn btn-outline-secondary" type="button" id="searchButton">Rechercher</button>
        		<a href="pdf.jsp" class="btn btn-primary ms-2">Générer Relévé</a>
    		</div>
		</div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID Envoi</th>
                    <th>Numéro Envoyeur</th>
                    <th>Numéro Récepteur</th>
                    <th>Montant</th>
                    <th>Date</th>
                    <th>Raison</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="envoyerTableBody">
            </tbody>
        </table>
    </div>
</body>
</html>