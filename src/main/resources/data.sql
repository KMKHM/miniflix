INSERT INTO videos (title, description, s3_key, thumbnail_key, duration, status, created_at, updated_at)
VALUES
    ('Big Buck Bunny', '오픈소스 단편 애니메이션', 'videos/big-buck-bunny.mp4', 'thumbnails/bbb.jpg', 596, 'ACTIVE', NOW(), NOW()),
    ('Elephant Dream', '블렌더 재단 단편 영화', 'videos/elephant-dream.mp4', 'thumbnails/ed.jpg', 654, 'ACTIVE', NOW(), NOW()),
    ('Sintel', '블렌더 오픈 무비', 'videos/sintel.mp4', 'thumbnails/sintel.jpg', 888, 'ACTIVE', NOW(), NOW()),
    ('Tears of Steel', 'SF 단편 영화', 'videos/tears-of-steel.mp4', 'thumbnails/tos.jpg', 734, 'ACTIVE', NOW(), NOW());