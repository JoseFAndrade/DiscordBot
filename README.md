# DiscordBot
A discord bot that is going to be able to do various things such as: play music, obtain information related to certain games, and do 
  miscellaneous things. 

# Technology Stack 
  - IntelliJ IDEA: Used this IDE because its functionality allows me to be able to write code efficiently. An example is how I used the Database Plugin within 
      the program to quickly be able to query the database and error check. 
  - DataGrip: Allows me to easily create new tables as well as quickly change values within a table to check for certain errors. 
  - Java: It's a language that I am extremely familiar with and I wanted to use it for this project to expand my knowledge of Java. 
  - JDA: The Java Discord API library was used to remove the REST API layer that I am still not well educated in. This allows me to be able to
      easily create a bot and focus on the things that I want to learn.
  - XAMPP: Contains a straightforward way to create your own SQL (MariaDB) database on your computer. I used this to host my server.
  - WebScraping Project: I created my webscraping project that takes information from wiki pages for games that I  play. This information is then put into a database and used for this project. In the future, I do want to turn it into an API so that this code does not interact with the database but instead interacts with the API. 

# Why I created this project
  - I had created a Python Discord bot back in college but never got around to adding in all the features I had ideas about because my programming wasn't 
      well developed yet. I now felt confident in my ability to learn and implement everything that I wanted to into my bot. I also wanted to refine the concepts
      that I had learned in college. Some of these concepts include the creation and manipulation of Databases, extracting and adding information into databases through
      Java, putting into practice some of the design patterns I had learned such as Singletons, Prototype, etc. While it was fun doing all of this, in the end
      I want to create a bot that gets used by my friends and me. 
      
# Problems that I have faced or am facing right now
  - Organization: The project organization is bad. While I attempted to organize the project, there needs to be more improvement made in the organization of
      where certain code should go and in what package. 
  - Comments: There are not a lot of comments on my files which is an issue because other people should understand my functions without needing to read
      every line of code. 
  - Best Practices: The need to re-write code is extremely important because there is messy code that I write to get to the solution of a problem that I am facing. When I returned to it, I realized that the code was written badly and needed to be revised. I have already been applying this and have reduced 
      multiple lines of code into one function that allowed me to use it in many different parts of my code. I need to keep going through my code and finding those pieces of code that I can easily reduce.
  - Version Migration: Currently trying to go from JDA 4 to JDA 5 since the project was first written with JDA 4. 
