-- vue.`user` definition

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL,
  `role` tinyint DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `stat` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

INSERT INTO vue.`user` (name,email,`role`,phone,stat) VALUES
	 ('yhn','123@qq.com',1,'13200223311',1),
	 ('rfv','456@qq.com',2,'16822334455',1),
	 ('qaz','789@qq.com',1,'16933220011',0);
