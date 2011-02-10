--
-- Table structure for table regularexpression_regular_expression
--
DROP TABLE IF EXISTS regularexpression_regular_expression;
CREATE TABLE regularexpression_regular_expression (
	id_expression int default 0 NOT NULL,
	title varchar(255),
	regular_expression_value long varchar,
	valid_exemple long varchar,
	information_message long varchar,
	error_message long varchar,
	PRIMARY KEY (id_expression)
);
