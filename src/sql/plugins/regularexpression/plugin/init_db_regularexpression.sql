--
-- Dumping data in table regularexpression_regular_expression
--
INSERT INTO regularexpression_regular_expression (id_expression, title, regular_expression_value, valid_exemple, information_message, error_message) VALUES ('1', 'Fichier JPG', 'image\/jpeg', 'image/jpeg', 'Expression régulière pour les fichiers de type jpeg.', 'Le format du fichier n''est pas valide. Veuillez choisir une image de type jpeg.');
INSERT INTO regularexpression_regular_expression (id_expression, title, regular_expression_value, valid_exemple, information_message, error_message) VALUES ('2', 'Email', '(^([a-zA-Z0-9]+(([\\.\\-\\_]?[a-zA-Z0-9]+)+)?)\\@(([a-zA-Z0-9]+[\\.\\-\\_])+[a-zA-Z]{2,4})$)|(^$)', 'admin@lutece.fr', 'Expression régulière pour les emails.', 'Le format de l''email est incorrect.');
