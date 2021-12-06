# Predator-Prey-Simulator

**Predator-Prey-Simulator** is an java application that has an AI learn how to play the included game.

## Video Walkthroughs

Final Product of Proposal
<img src='walkthroughOld.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Separate Agent Training
 - Certain # generations training before training target swap
 - 50 generations ideal
Training Mode
 - Increases speed, now takes 1/10 time

Generation 237: Mouse Learns to dodge cat when training by itself
<img src='walkthroughDodgeOld.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Implemented Gene Crossover
 - Crossover rate of 0.6
 - Previously each genes have 50% come from either parent
Redesigned Action Calculation
 - State-based genetics
Refined Gene Hyper-parameters
 - Increase population size 20-52
 - Decreased mutation rate 0.05-0.001
 - Roughly 6.552 mutations per generation
Refined Fitness Test
 - Switch From Linear to Exponential Goal Step
 - Cat score influence from time now
 - Reduced chance of stuck
Offset Cat
 - Reduces Mouses inclination to run into the cat

Generation 25: Mouse begins to understand grabbing fruit
<img src='walkthrough25.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Generation 150: Mouse and Cat develop simple techniques for achieving their goals
<img src='walkthrough150.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Generation 288: Cat learns to swat causing both agents' techniques to become janky
<img src='walkthrough288.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Generation 514: Mouse learns to repel causing both agents' techniques to become janky
<img src='walkthrough514.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Generation 728: Mouse refines repel technique, cat consistently loses
<img src='walkthrough728.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
