-- Database setup script for BDMobile
-- Run this script as postgres superuser

-- Create database if it doesn't exist
SELECT 'CREATE DATABASE "BDMobile"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'BDMobile')\gexec

-- Connect to the database
\c BDMobile

-- The application will create tables automatically with Hibernate
-- This script just ensures the database exists

-- Verify connection
SELECT 'Database BDMobile is ready!' as status;