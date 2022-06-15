Instead of focusing on test coverage, my aim here was to create the foundations for a suite of tests that is simple to expand on. 

I have used a design pattern that lets us reuse test steps and write tests out in simple English. One thing that may stand out is the lack of idling resource. I have found idling resources adds some flakiness when used in large test suites.
Instead I have used a wait class that traverses the view tree for the required element until the item loads or it times out. 