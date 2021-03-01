import pygame
from random import randrange
 
RES = 660
SIZE = 30
 
x,y = randrange(0, RES, SIZE), randrange(0, RES, SIZE)
apple=randrange(0, RES, SIZE), randrange(0, RES, SIZE)
 
length = 1
snake = [(x, y)]
dx, dy = 0, 0
fps = 4
 
pygame.init()
sc = pygame.display.set_mode([RES, RES])
clock = pygame.time.Clock()
 
while True:
    sc.fill(pygame.Color('black'))
    [(pygame.draw.rect(sc, pygame.Color('green'), (i, j, SIZE, SIZE))) for i, j in snake]
    pygame.draw.rect(sc, pygame.Color('red'), (*apple, SIZE, SIZE))
 
    x+=dx*SIZE
    y+=dy*SIZE
    snake.append((x, y))
    snake=snake[-length:]
 
    #якщо з'їсти червоне
    if snake[-1]==apple:
        apple=randrange(0, RES, SIZE), randrange(0, RES, SIZE)
        length+=1
        fps+=1
 
    #якщо програти
    if x<0 or x> RES - SIZE or y<0 or y> RES - SIZE:
        print("Ви програли :(")
        break
    if len(snake) != len(set(snake)):
        print("Ви програли :(")
        break
 
    if len(snake) == 660:
        print("Ви виграли :)")
       
     
    
    pygame.display.flip()    
    clock.tick(fps)
    
    for event in pygame.event.get():
        if event.type==pygame.QUIT:
            exit()
        
        
    key = pygame.key.get_pressed()
    if key[pygame.K_u]:
        dx, dy = 0, -1
    if key[pygame.K_j]:
        dx, dy = 0, 1
    if key[pygame.K_h]:
        dx, dy = -1, 0
    if key[pygame.K_k]:
        dx, dy = 1, 0  
