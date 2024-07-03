<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Générer le relevé d'opérations</title>
    <link rel="stylesheet" href="../bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <script src="../bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .custom-card {
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card custom-card">
                    <div class="card-body p-5">
                        <h2 class="card-title text-center mb-4">Générer le relevé d'opérations</h2>
                        <form action="http://localhost:8080/GTAL/api/envoyer/generatePDF" method="get" target="_blank">
                            <div class="mb-3">
                                <label for="numtel" class="form-label">Numéro de téléphone</label>
                                <select id="numtel" name="numtel" class="form-select" required>
                                    <option value="" disabled selected>Sélectionnez un numéro</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="month" class="form-label">Mois (1-12)</label>
                                <input type="number" id="month" name="month" class="form-control" min="1" max="12" required>
                            </div>
                            <div class="mb-3">
                                <label for="year" class="form-label">Année</label>
                                <input type="number" id="year" name="year" class="form-control" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">Générer PDF</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
    window.onload = function() {
        console.log("Window loaded, fetching phone numbers...");
        fetch('http://localhost:8080/GTAL/api/envoyer/phoneNumbers')
            .then(response => {
                console.log("Response received:", response);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(phoneNumbers => {
                console.log("Phone numbers received:", phoneNumbers);
                const select = document.getElementById('numtel');
                if (phoneNumbers && phoneNumbers.length > 0) {
                    phoneNumbers.forEach(number => {
                        const option = document.createElement('option');
                        option.value = number;
                        option.textContent = number;
                        select.appendChild(option);
                    });
                    console.log("Select options added");
                } else {
                    console.log("No phone numbers received or empty array");
                }
            })
            .catch(error => {
                console.error("Error fetching phone numbers:", error);
            });
    };
    </script>
</body>
</html>