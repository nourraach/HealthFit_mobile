-- SQL script to add users directly to the database
-- Note: Passwords should be hashed in production, but for testing we'll use plain text

-- Add sample users
INSERT INTO users (
    nom, 
    prenom, 
    num_tel, 
    adresse_email, 
    mot_de_passe, 
    date_naissance,
    taille,
    poids,
    sexe,
    objectif,
    niveau_activite
) VALUES 
-- Admin user
(
    'Admin', 
    'User', 
    '1234567890', 
    'admin@example.com', 
    'admin123', 
    '1990-01-01',
    1.75,
    70.0,
    'HOMME',
    'MAINTIEN',
    'MODERE'
),

-- Test user 1
(
    'Dupont', 
    'Marie', 
    '0123456789', 
    'marie.dupont@example.com', 
    'password123', 
    '1995-05-15',
    1.65,
    60.0,
    'FEMME',
    'PERTE_POIDS',
    'LEGER'
),

-- Test user 2
(
    'Martin', 
    'Pierre', 
    '0987654321', 
    'pierre.martin@example.com', 
    'password123', 
    '1988-12-20',
    1.80,
    85.0,
    'HOMME',
    'PRISE_MASSE',
    'INTENSE'
),

-- Test user 3
(
    'Bernard', 
    'Sophie', 
    '0555123456', 
    'sophie.bernard@example.com', 
    'password123', 
    '1992-08-10',
    1.70,
    65.0,
    'FEMME',
    'MAINTIEN',
    'MODERE'
);

-- Verify the users were added
SELECT 
    id,
    nom,
    prenom,
    adresse_email,
    date_naissance,
    objectif
FROM users 
ORDER BY id;

-- Show user count
SELECT COUNT(*) as total_users FROM users;