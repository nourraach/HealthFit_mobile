-- Backend Verification SQL Script
-- Run this to check user program data for abir@gmail.com

-- 1. Check user program details
SELECT 
    up.id as user_programme_id,
    up.date_debut,
    up.date_fin_prevue,
    up.statut,
    p.nom as programme_nom,
    p.duree_jours,
    u.adresse_email,
    -- Calculate expected end date
    (up.date_debut + INTERVAL '1 day' * (p.duree_jours - 1)) as calculated_end_date,
    -- Check if dates are consistent
    CASE 
        WHEN up.date_fin_prevue = (up.date_debut + INTERVAL '1 day' * (p.duree_jours - 1)) 
        THEN '✅ Dates cohérentes' 
        ELSE '❌ Dates incohérentes' 
    END as date_consistency,
    -- Check current day
    CASE 
        WHEN CURRENT_DATE BETWEEN up.date_debut AND up.date_fin_prevue 
        THEN '✅ Dans la période' 
        ELSE '❌ Hors période' 
    END as current_status
FROM user_programmes up
JOIN programmes p ON up.programme_id = p.id
JOIN users u ON up.user_id = u.id
WHERE u.adresse_email = 'abir@gmail.com' 
ORDER BY up.date_debut DESC;

-- 2. Check existing progressions for this user
SELECT 
    pj.id,
    pj.date,
    pj.statut_jour,
    pj.score_jour,
    COUNT(pjp.plat_id) as plats_count,
    COUNT(pja.activite_id) as activites_count
FROM user_programmes up
JOIN programmes p ON up.programme_id = p.id
JOIN users u ON up.user_id = u.id
LEFT JOIN progressions_journalieres pj ON pj.user_programme_id = up.id
LEFT JOIN progression_plats pjp ON pjp.progression_id = pj.id
LEFT JOIN progression_activites pja ON pja.progression_id = pj.id
WHERE u.adresse_email = 'abir@gmail.com' 
  AND up.statut = 'EN_COURS'
GROUP BY pj.id, pj.date, pj.statut_jour, pj.score_jour
ORDER BY pj.date DESC
LIMIT 10;

-- 3. Check if today's progression exists
SELECT 
    CASE 
        WHEN EXISTS (
            SELECT 1 FROM progressions_journalieres pj
            JOIN user_programmes up ON pj.user_programme_id = up.id
            JOIN users u ON up.user_id = u.id
            WHERE u.adresse_email = 'abir@gmail.com' 
              AND pj.date = CURRENT_DATE
              AND up.statut = 'EN_COURS'
        ) 
        THEN '✅ Progression existe pour aujourd''hui' 
        ELSE '❌ Aucune progression pour aujourd''hui' 
    END as today_progression_status;

-- 4. Check program plats and activities
SELECT 
    'Plats disponibles' as type,
    COUNT(*) as count
FROM programme_plats pp
JOIN user_programmes up ON pp.programme_id = up.programme_id
JOIN users u ON up.user_id = u.id
WHERE u.adresse_email = 'abir@gmail.com' 
  AND up.statut = 'EN_COURS'

UNION ALL

SELECT 
    'Activités disponibles' as type,
    COUNT(*) as count
FROM programme_activites pa
JOIN user_programmes up ON pa.programme_id = up.programme_id
JOIN users u ON up.user_id = u.id
WHERE u.adresse_email = 'abir@gmail.com' 
  AND up.statut = 'EN_COURS';

-- 5. Detailed program info
SELECT 
    p.nom,
    p.description,
    p.duree_jours,
    p.objectif,
    up.date_debut,
    up.date_fin_prevue,
    up.statut,
    up.poids_debut,
    up.poids_actuel,
    up.poids_objectif
FROM programmes p
JOIN user_programmes up ON p.id = up.programme_id
JOIN users u ON up.user_id = u.id
WHERE u.adresse_email = 'abir@gmail.com' 
  AND up.statut = 'EN_COURS';