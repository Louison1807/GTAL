-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : mer. 03 juil. 2024 à 08:52
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `GTAL`
--

-- --------------------------------------------------------

--
-- Structure de la table `CLIENT`
--

CREATE TABLE `CLIENT` (
  `numtel` varchar(10) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `sexe` varchar(1) NOT NULL,
  `pays` varchar(255) NOT NULL,
  `solde` int(50) NOT NULL,
  `mail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `CLIENT`
--

INSERT INTO `CLIENT` (`numtel`, `nom`, `sexe`, `pays`, `solde`, `mail`) VALUES
('0323895113', 'Bryan', 'M', 'Danmark', 1250420, 'hnlouison@gmail.com'),
('0325648963', 'Merllia', 'F', 'Soudan', 6319564, 'm@gmail.com'),
('0340000000', 'Xavier', 'M', 'Madagascar', 150, 'hnlouison@gmail.com'),
('0381295533', 'Flemming', 'M', 'Mali', 5003251, 'bienaimelouison@gmail.com'),
('0600000000', 'Max', 'M', 'France', 150, 'dollar.marielle@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `ENVOYER`
--

CREATE TABLE `ENVOYER` (
  `idEnv` varchar(50) NOT NULL,
  `numEnvoyeur` varchar(10) NOT NULL,
  `numRecepteur` varchar(10) NOT NULL,
  `montant` int(30) NOT NULL,
  `date` datetime NOT NULL,
  `raison` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ENVOYER`
--

INSERT INTO `ENVOYER` (`idEnv`, `numEnvoyeur`, `numRecepteur`, `montant`, `date`, `raison`) VALUES
('E1', '0323895113', '0325648963', 5000, '2024-07-01 00:00:00', 'achat'),
('E2', '0325648963', '0323895113', 250000, '2024-06-28 00:00:00', 'facture'),
('E3', '0600000000', '0340000000', 150, '2024-07-01 00:00:00', 'vacance'),
('E4', '0600000000', '0323895113', 200, '2024-07-01 00:00:00', 'droit');

-- --------------------------------------------------------

--
-- Structure de la table `FRAIS`
--

CREATE TABLE `FRAIS` (
  `idfrais` varchar(50) NOT NULL,
  `montant1` int(30) NOT NULL,
  `montant2` int(30) NOT NULL,
  `frais` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `FRAIS`
--

INSERT INTO `FRAIS` (`idfrais`, `montant1`, `montant2`, `frais`) VALUES
('F1', 1000, 7100, 5000),
('F2', 1515454, 55455484, 1545454),
('F3', 20000000, 4000000, 125500),
('F4', 500000, 5000120, 1200),
('F5', 100, 200, 5);

-- --------------------------------------------------------

--
-- Structure de la table `TAUX`
--

CREATE TABLE `TAUX` (
  `idtaux` varchar(50) NOT NULL,
  `montant1` int(30) NOT NULL,
  `montant2` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `TAUX`
--

INSERT INTO `TAUX` (`idtaux`, `montant1`, `montant2`) VALUES
('T1', 1000, 5000),
('T2', 5100, 60000),
('T3', 7000, 1200),
('T4', 1, 4800);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `CLIENT`
--
ALTER TABLE `CLIENT`
  ADD PRIMARY KEY (`numtel`);

--
-- Index pour la table `ENVOYER`
--
ALTER TABLE `ENVOYER`
  ADD PRIMARY KEY (`idEnv`),
  ADD KEY `envoyeur` (`numEnvoyeur`),
  ADD KEY `recepteur` (`numRecepteur`),
  ADD KEY `numEnvoyeur` (`numEnvoyeur`);

--
-- Index pour la table `FRAIS`
--
ALTER TABLE `FRAIS`
  ADD PRIMARY KEY (`idfrais`);

--
-- Index pour la table `TAUX`
--
ALTER TABLE `TAUX`
  ADD PRIMARY KEY (`idtaux`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `ENVOYER`
--
ALTER TABLE `ENVOYER`
  ADD CONSTRAINT `ENVOYER_ibfk_1` FOREIGN KEY (`numEnvoyeur`) REFERENCES `CLIENT` (`numtel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ENVOYER_ibfk_2` FOREIGN KEY (`numRecepteur`) REFERENCES `CLIENT` (`numtel`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
