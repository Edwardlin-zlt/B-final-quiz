### 完成度：
* 需求对应的功能代码都有，但是部分功能存在bug

__Details:__
- \- TraineeController.java:41 bug，无法正常调用
- \- GroupController.java:26 bug，无法正常调用
- \- TrainerController.java:40 bug，无法正常调用

### 测试：
* 有controller和unit测试，且覆盖较为全面

### 知识点：
* 对lombok的掌握不错
* 对三层架构的掌握不错
* 对Spring MVC注解的掌握不错
* 对异常处理的掌握不错

__Details:__
- \- Group.java:20  @OneToMany和@ManyToOne不一定要成对出现
- \- GroupController.java:27 按照restful实践，改变的资源，所以这里应该POST

### 工程实践：
* 分包合理

__Details:__
- \- TraineeRepositoryTest.java:47 不要提交注释代码，了解下@Disable
- \- Trainee.java:20 思考下，序列化需要将整个group都序列化出去吗？
- \- GroupService.java:33 如果不确认，提交前删掉。不要提交无必要的注释
- \- GroupService.java:56 Magic number 2

