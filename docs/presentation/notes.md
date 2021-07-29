1. Thank you all for coming and giving me the opportunity to present this idea.

2. Actually, more than preparing a conventional presentation and trying to sell you the best application for a customer, 
I decided that I wanted to tell you a story, the story of my friend Pisa.

-- SLIDE 2 --

3. Pisa is a Software engineer for a medium size company and she likes to define herself as a geeky nerd person.

4. On her daily basis she uses Slack as her tool for communicating with other colleagues and with some preocesses, like
JIRA, GITHUB, you name it.

5. Recently Pisa's company was acquired by a way bigger tech company 

-- SLIDE 3 --

6. So... What is the problem you might ask? A bigger company often brings new ideas, revenue, security...

7. The problem for my friend Pisa was that she was used to manage all her holidays, courses and timesheets with a pretty
cool platform, but the new LEADERSHIP brought some old fashion systems for HR that were based in these kind of black 
and blue or black and green screens. 

8. That was a headache for Pisa, as she hates bureaucracy.

-- SLIDE 4 --

9. Pisa put her Thinking hat on, and she started drafting an idea on how cool would be if she could do all those XXXX 
from the comfort of her favourite tool, from SLACK. She was even envisioning her manager approving her holidays directly
with the command tool or filling the timesheets in 2 clicks.

10. The idea of PISA was there. Personnel Integration Slack Application was born.

-- SLIDE 5 --

11. PISA is a program that you can install in Slack and it connects with the PISA-server.
 
12. The server uses a microservice architecture with the DB access decoupled from the business logic, and it is this
second service the one that is accessing to the database, the same one as the monolithic mainframe application that 
Pisa hates.

12. Slack provides all the security and credentials for using the application, as well as the infrastructure for
accessing in a responsive way, via web, mobile, tablet, etc.

13. The servers are deployed in Heroku and they are written in Java, using Slack-bolt as the library for handling
the events with the Slack app.

13. And now, before we continue with the presentation, let me show you a taste of what PISA is capable of.

-- DEMO --

14. we can see the landing page with a short version of the time sheets
15. we have the reports for the  last week of work and this week, with the enojis updating according to the status
SELECT project!
16. Submit - error
17. open dialogs
18. After the demo, we can go back to the presentation

-- SLIDE 6 --

20. Results and Future

21. First I need to clarify that the app is not ready for production, as I didn't have time to decouple it into at least 
2 microservices and it is a monolithic version so far. Also, it doesn;t have any tests, which is not acceptable for any
release, but ok for a demo.
  
22. Said that, the integration can be used as a de-facto new HR system and the company doesn't need to inves in frontend.

23. The users, employees, are happier, as they don't need to go to those old systems

24. Also, there are many capabilities that we can include, like integrating automatically with meetings, automatic
messages to remind you filling timesheets, your manager approving holidays, etc.

25. In the end, I believe the employee's happiness translates into a better work and more performance, which is 
automatically reflected on how we treat our customers.

-- SLIDE 7 --

26. With this, I conclude the story of my friend Pisa and how she made life and her colleague's life easier.

27. Any questions?

-- SLIDE 8 --

Thanks! 
