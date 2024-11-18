-- Inserir Estados, se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM "Estado" WHERE "UF" = 'AC') THEN
        INSERT INTO "Estado"("UF") VALUES
            ('AC'), ('AL'), ('AP'),
            ('AM'), ('BA'), ('CE'),
            ('ES'), ('GO'), ('MA'),
            ('MT'), ('MS'), ('MG'),
            ('PA'), ('PB'), ('PR'),
            ('PE'), ('PI'), ('RJ'),
            ('RN'), ('RS'), ('RO'),
            ('RR'), ('SC'), ('SP'),
            ('SE'), ('TO'), ('DF');
    END IF;
END $$;

-- Inserir Categorias, se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM "Categoria" WHERE "id" = 1) THEN
        INSERT INTO "Categoria" VALUES
            (1, 'Casa Inteligente'),
            (2, 'Computadores'),
            (3, 'Eletrônicos'),
            (4, 'Hardware'),
            (5, 'Kits'),
            (6, 'Monitores'),
            (7, 'Notebooks e Portáteis'),
            (8, 'Periféricos'),
            (9, 'Realidade Virtual'),
            (10, 'Redes e wireless'),
            (11, 'Video Games');
    END IF;
END $$;

-- Inserir Sub-Categorias, se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM "Sub_Categoria" WHERE "id" = 1) THEN
        INSERT INTO "Sub_Categoria" VALUES
            (1, 'Assistente Virtual', 1),
            (2, 'Controles Smarts', 1),
            (3, 'Lâmpadas Inteligentes', 1),
            (4, 'Sensores', 1),
            (5, 'Computadores Gamers', 2),
            (6, 'Computadores Workstation', 2),
            (7, 'Acessórios de Console', 3),
            (8, 'Carregadores', 3),
            (9, 'Refrigeração', 3),
            (10, 'Smart Box', 3),
            (11, 'Armazenamento', 4),
            (12, 'Coolers', 4),
            (13, 'Fonte', 4),
            (14, 'Memória RAM', 4),
            (15, 'Placa de Vídeo', 4),
            (16, 'Placa Mãe', 4),
            (17, 'Processadores', 4),
            (18, 'Gamer', 5),
            (19, 'Periféricos', 5),
            (20, 'Upgrade', 5),
            (21, 'Monitores Gamers', 6),
            (22, 'Monitores Workstation', 6),
            (23, 'Notebooks', 7),
            (24, 'Smartphones', 7),
            (25, 'Tablets', 7),
            (26, 'Smart Watch', 7),
            (27, 'Caixa de Som', 8),
            (28, 'Fone de Ouvido', 8),
            (29, 'Microfone', 8),
            (30, 'Mouse', 8),
            (31, 'Mousepad', 8),
            (32, 'Teclado', 8),
            (33, 'Óculos de VR', 9),
            (34, 'Periféricos de VR', 9),
            (35, 'Access Point', 10),
            (36, 'Adaptadores', 10),
            (37, 'Cabos', 10),
            (38, 'Cabos de Redes', 10),
            (39, 'Roteadores', 10),
            (40, 'Switches', 10),
            (41, 'Console de Mesa', 11),
            (42, 'Portátil', 11);
    END IF;
END $$;

-- Inserir Descontos, se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM "Desconto" WHERE "Valor_Porcentagem" = '0') THEN
        INSERT INTO "Desconto"("Valor_Porcentagem") VALUES
            ('0'), ('1'), ('2'), ('3'), ('4'),
            ('5'), ('6'), ('7'), ('8'), ('9'),
            ('10'), ('11'), ('12'), ('13'), ('14'),
            ('15'), ('16'), ('17'), ('18'), ('19'),
            ('20'), ('21'), ('22'), ('23'), ('24'),
            ('25'), ('26'), ('27'), ('28'), ('29'),
            ('30'), ('31'), ('32'), ('33'), ('34'),
            ('35'), ('36'), ('37'), ('38'), ('39'),
            ('40'), ('41'), ('42'), ('43'), ('44'),
            ('45'), ('46'), ('47'), ('48'), ('49'),
            ('50'), ('51'), ('52'), ('53'), ('54'),
            ('55'), ('56'), ('57'), ('58'), ('59'),
            ('60'), ('61'), ('62'), ('63'), ('64'),
            ('65'), ('66'), ('67'), ('68'), ('69'),
            ('70'), ('71'), ('72'), ('73'), ('74'),
            ('75'), ('76'), ('77'), ('78'), ('79'),
            ('80'), ('81'), ('82'), ('83'), ('84'),
            ('85'), ('86'), ('87'), ('88'), ('89'),
            ('90'), ('91'), ('92'), ('93'), ('94'),
            ('95'), ('96'), ('97'), ('98'), ('99');
    END IF;
END $$;