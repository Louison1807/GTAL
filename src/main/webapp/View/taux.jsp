<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Taux</title>
    <link rel="stylesheet" href="../bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="../js/taux.js"></script>
    <style>
        .hidden {
            display: none;
        }
    </style>
</head>
<body onload="getAllTaux()">
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
        <h1 class="mb-4">Gestion des Taux</h1>

        <div id="insertFormContainer" class="mb-4">
            <h2 class="mb-3">Créer un nouveau taux</h2>
            <form id="createForm" onsubmit="createTaux(event)" class="row g-3">
                <div class="col-md-4">
                    <input type="text" class="form-control" id="createIdtaux" placeholder="ID Taux" required>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="createMontant1" placeholder="Montant 1" required>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="createMontant2" placeholder="Montant 2" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Créer</button>
                </div>
            </form>
        </div>

        <div id="updateFormContainer" class="mb-4 hidden">
            <h2 class="mb-3">Mettre à jour un taux</h2>
            <form id="updateForm" onsubmit="updateTaux(event)" class="row g-3">
                <div class="col-md-4">
                    <input type="text" class="form-control" id="updateIdtaux" placeholder="ID Taux" readonly>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="updateMontant1" placeholder="Montant 1" required>
                </div>
                <div class="col-md-4">
                    <input type="number" class="form-control" id="updateMontant2" placeholder="Montant 2" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                    <button type="button" class="btn btn-secondary" onclick="showInsertForm()">Annuler</button>
                </div>
            </form>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>ID Taux</th>
                        <th>Montant 1</th>
                        <th>Montant 2</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="tauxTableBody">
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>