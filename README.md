# SpringHibernateMoviesExercise
___
This is a sample project that uses Spring and Hibernate for a console application very simple,
is not created like a guide about how things should be done but like a way to show how you
can configure this technologies in non-SpringBoot apps.

### Technologies

- Java 11
- Maven 3
- Hibernate 5.4.x
- Spring Core/Context 5.2.x
- H2 in memory database

### Structure of the project

```shell
root
|
src
└── main
    ├── java
    │   └── io
    │       └── github
    │           └── joxebus
    │               ├── App.java
    │               ├── entity
    │               │   ├── Movie.java
    │               │   └── TableEntity.java
    │               ├── repository
    │               │   ├── GenericRepository.java
    │               │   ├── MovieRepository.java
    │               │   └── Repository.java
    │               ├── service
    │               │   ├── MovieService.java
    │               │   └── MovieServiceImpl.java
    │               └── util
    │                   └── Utilities.java
    └── resources
        ├── applicationContext.xml
        └── hibernate.cfg.xml
```

### Entity package

This package contains a sample class `Movie` which is mapped via annotations and also implements
an interface called TableEntity, this interface is used to have access to the `id` field 
which will be used to perform operations with the database, this part of the code is know as `model layer`

### Repository package

Here you can find 3 different classes which are described in the following lines that interact with the data directly
we can say that this is part of the `data layer`

- `Repository.java`: It's an interface that describe the CRUD operations available for the Entities,
it uses Generics to specify that only support operations over classes that implements the `TableEntity` interface.
- `GenericRepository.java`: It's an abstract class that defines the CRUD operations for objects for the `Entity` classes
that implements also the `TableEntity` interface.
- `MovieRepository.java`: This class is a concrete class that define the CRUD operations for `Movie` objects.

### Service package

Following the layer format this package contains the interface and implementation with the logic to interact with 
the logic of the application for `Movie` objects, using the `MovieRepository` to interact with the database operations, 
we can say this is the `business layer`

- `MovieService.java`: It's the interface that define the actions available for the services that want to implement it.
- `MovieServiceImpl.java`: It's the concrete class that contain the logic to interact with the data layer.

### Util package

The configuration to work with Spring and Hibernate is here, at the same time the `Utilities` class use the resources
placed on `src/main/resources`


### App.java

This is the entry point of the program, once it runs you will see a menu where you can select the different kind of operations,
this application is trying to follow the `SOLID` principles, please take a look to the JavaDoc on each class for more information.