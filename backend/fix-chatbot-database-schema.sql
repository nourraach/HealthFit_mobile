-- 🔧 FIX CHATBOT DATABASE SCHEMA
-- Problem: contenu field in messages table is VARCHAR(255) but chatbot responses are longer
-- Solution: Change to TEXT type to allow unlimited length

-- Connect to your PostgreSQL database and run this script

-- 1. Check current column type
SELECT column_name, data_type, character_maximum_length 
FROM information_schema.columns 
WHERE table_name = 'messages' AND column_name = 'contenu';

-- 2. Update the column to TEXT type
ALTER TABLE messages ALTER COLUMN contenu TYPE TEXT;

-- 3. Verify the change
SELECT column_name, data_type, character_maximum_length 
FROM information_schema.columns 
WHERE table_name = 'messages' AND column_name = 'contenu';

-- 4. Also check community_messages table (should already be TEXT)
SELECT column_name, data_type, character_maximum_length 
FROM information_schema.columns 
WHERE table_name = 'community_messages' AND column_name = 'contenu';

-- Expected result after fix:
-- messages.contenu should be TEXT with no character limit
-- community_messages.contenu should be TEXT with no character limit