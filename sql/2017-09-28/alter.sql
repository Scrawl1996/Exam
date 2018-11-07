/*修改题表的非空验证*/
ALTER TABLE `exam`.`questions`     CHANGE `question` `question` VARCHAR(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `answer` `answer` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `type` `type` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `level` `level` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `employeeId` `employeeId` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ;
/*修改选项表的非空验证 **/
ALTER TABLE `exam`.`options`     CHANGE `optionContent` `optionContent` VARCHAR(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `optionSequence` `optionSequence` VARCHAR(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,     CHANGE `isAnswer` `isAnswer` VARCHAR(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL ;