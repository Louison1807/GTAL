<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Clients</title>
     <link rel="stylesheet" href="../bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="../js/client.js"></script>
</head>
 <style>
        .hidden {
            display: none;
        }
    </style>
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
        <h1 class="mb-4">Gestion des Clients</h1>

        <div id="insertFormContainer" class="mb-4">
            <h2 class="mb-3">Créer un nouveau client</h2>
            <form id="createForm" class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" id="createNumtel" placeholder="Numéro de téléphone" required>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="createNom" placeholder="Nom" required>
                </div>
                <div class="col-md-4">
                    <select id="createSexe" class="form-select" required>
                        <option value="">Sélectionnez le sexe</option>
                        <option value="M">M</option>
                        <option value="F">F</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="createPays" placeholder="Pays" required>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="createSolde" placeholder="Solde" required>
                </div>
                <div class="col-md-12">
                    <input type="email" class="form-control" id="createMail" placeholder="Email" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Créer</button>
                </div>
            </form>
        </div>

        <div id="updateFormContainer" class="mb-4 hidden">
            <h2 class="mb-3">Mettre à jour un client</h2>
            <form id="updateForm" class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" id="updateNumtel" placeholder="Numéro de téléphone" readonly>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="updateNom" placeholder="Nom" required>
                </div>
                <div class="col-md-4">
                    <select id="updateSexe" class="form-select" required>
                        <option value="M">M</option>
                        <option value="F">F</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="updatePays" placeholder="Pays" required>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="updateSolde" placeholder="Solde" required>
                </div>
                <div class="col-md-12">
                    <input type="email" class="form-control" id="updateMail" placeholder="Email" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                    <button type="button" class="btn btn-secondary" onclick="showInsertForm()">Annuler</button>
                </div>
            </form>
        </div>

         <div class="mb-4">
            <div class="input-group">
                <input type="text" class="form-control" id="searchInput" placeholder="Rechercher un client">
                <button class="btn btn-outline-secondary" type="button" id="searchButton">Rechercher</button>
            </div>
        </div>

       <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Numéro de téléphone</th>
                        <th>Nom</th>
                        <th>Sexe</th>
                        <th>Pays</th>
                        <th>Solde</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="clientTableBody">
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
