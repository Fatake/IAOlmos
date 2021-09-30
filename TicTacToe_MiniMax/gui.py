from tkinter import *
from tkinter import messagebox # Caja de texto
from tkinter import simpledialog


def imprimeTablero(tablero):
    print(tablero[1] + '|' + tablero[2] + '|' + tablero[3])
    print('-----')
    print(tablero[4] + '|' + tablero[5] + '|' + tablero[6])
    print('-----')
    print(tablero[7] + '|' + tablero[8] + '|' + tablero[9])
    print("\n")

def existeEspacio(posicion):
    if posicion > 9:
        return False
    if tablero[posicion] == ' ':
        return True
    else:
        return False

def insertaFicha(ficha, posicion):
    if existeEspacio(posicion):
        tablero[posicion] = ficha
        imprimeTablero(tablero)
        if (checaEmpate()):
            print("[i]Empate")
            exit()
        if checaGana():
            if ficha == 'X':
                print("[i]Gana la IA")
                exit()
            else:
                print("[i]Gana el jugador")
                exit()
        return
    else:
        print("[!] No se puede insertar la ficha en {1}".format(posicion))
        posicion = int(input("[+] Ingresa un nueva casilla \n-> "))
        insertaFicha(ficha, posicion)
        return

def checaGana():
    if (tablero[1] == tablero[2] and tablero[1] == tablero[3] and tablero[1] != ' '):
        return True
    elif (tablero[4] == tablero[5] and tablero[4] == tablero[6] and tablero[4] != ' '):
        return True
    elif (tablero[7] == tablero[8] and tablero[7] == tablero[9] and tablero[7] != ' '):
        return True
    elif (tablero[1] == tablero[4] and tablero[1] == tablero[7] and tablero[1] != ' '):
        return True
    elif (tablero[2] == tablero[5] and tablero[2] == tablero[8] and tablero[2] != ' '):
        return True
    elif (tablero[3] == tablero[6] and tablero[3] == tablero[9] and tablero[3] != ' '):
        return True
    elif (tablero[1] == tablero[5] and tablero[1] == tablero[9] and tablero[1] != ' '):
        return True
    elif (tablero[7] == tablero[5] and tablero[7] == tablero[3] and tablero[7] != ' '):
        return True
    else:
        return False

def checaGanaFicha(mark):
    if tablero[1] == tablero[2] and tablero[1] == tablero[3] and tablero[1] == mark:
        return True
    elif (tablero[4] == tablero[5] and tablero[4] == tablero[6] and tablero[4] == mark):
        return True
    elif (tablero[7] == tablero[8] and tablero[7] == tablero[9] and tablero[7] == mark):
        return True
    elif (tablero[1] == tablero[4] and tablero[1] == tablero[7] and tablero[1] == mark):
        return True
    elif (tablero[2] == tablero[5] and tablero[2] == tablero[8] and tablero[2] == mark):
        return True
    elif (tablero[3] == tablero[6] and tablero[3] == tablero[9] and tablero[3] == mark):
        return True
    elif (tablero[1] == tablero[5] and tablero[1] == tablero[9] and tablero[1] == mark):
        return True
    elif (tablero[7] == tablero[5] and tablero[7] == tablero[3] and tablero[7] == mark):
        return True
    else:
        return False

def checaEmpate():
    for key in tablero.keys():
        if (tablero[key] == ' '):
            return False
    return True

def movimientoJugador():
    posicion = int(input("[+] Ingresa una casilla para tirar 'O'\n-> "))
    insertaFicha(jugadorFicha, posicion)
    return

def movimientoComputadora():
    bestScore = -800
    bestMove = 0
    for key in tablero.keys():
        # checa el peso de cada lugar
        if (tablero[key] == ' '):
            tablero[key] = computadoraIA
            # Optiene un score
            score = minimax(tablero, 0, False)
            tablero[key] = ' '
            # optiene el mejor resultado
            if (score > bestScore):
                bestScore = score
                bestMove = key

    insertaFicha(computadoraIA, bestMove)
    return

def minimax(tablero, depth, isMaximizing):
    '''
    Funcion minimax donde dado un tablero pregunta la siguiente funcion de evaluacion
    f(talero_n) = 0 si existe empate
    f(talero_n) = 1 si max Gana (en este caso max es la IA)
    f(talero_n) = -1 si min Gana (en este caso el jugador gana)

    '''
    if (checaGanaFicha(computadoraIA)):
        return 1
    elif (checaGanaFicha(jugadorFicha)):
        return -1
    elif (checaEmpate()):
        return 0

    if (isMaximizing): # Es maximo
        bestScore = -800
        for key in tablero.keys():
            if (tablero[key] == ' '):
                tablero[key] = computadoraIA
                score = minimax(tablero, depth + 1, False)
                tablero[key] = ' '
                if (score > bestScore): # elije el mayor 
                    bestScore = score
        return bestScore
    else: # es minimo
        bestScore = 800
        for key in tablero.keys():
            if (tablero[key] == ' '):
                tablero[key] = jugadorFicha
                score = minimax(tablero, depth + 1, True)
                tablero[key] = ' '
                if (score < bestScore): # Elije el menor
                    bestScore = score
        return bestScore

def clearScreen():
    if os.name == 'posix':
        _ = os.system('clear')
    else:
        _ = os.system('cls')

def casillasDisponibles():
    print("[i] Estas son las casillas:")
    print("1, 2, 3 ")
    print("4, 5, 6 ")
    print("7, 8, 9 \n")

def bloquearBotones():
    for i in range(0,9):
        listaBotones[i].config(state="disable")

def iniciaJuego():
    for i in range(0,9):
        listaBotones[i].config(state="normal")
        listaBotones[i].config(bg="lightgray")
        listaBotones[i].config(text="-")
        tablero[i+1] = "n"

    turnoElegido = simpledialog.askinteger("Orden","Ingrese 1 para ser el primer jugador o 2 para el segundo")
    nombre = simpledialog.askstring("jugador1","Escriba su nombre")

    if nombre == "":
        nombre = "null"
    
    global jugador1, jugador2
    if turnoElegido == 1:
        print("[+] Se eligio primer jugador")
        jugador1 = nombre
        jugador2 = "Bob La IA"
        contadorIATurno = 2
    else:
        print("[+] Se eligio segundo jugador")
        jugador1 = "Bob La IA"
        jugador2 = nombre
        contadorIATurno = 1

    turnoJugador.set("Turno: "+jugador1)

def cambiar(numero):
    global jugador1,jugador2,contadorTurno
    print("turno {}".format(contadorTurno))
    print(numero)
    print(tablero[numero])
    if tablero[numero] == "n" and contadorTurno == 1:
        listaBotones[numero-1].config(text="X")
        listaBotones[numero-1].config(bg="white")
        tablero[numero] = "x"
        contadorTurno = 2
        turnoJugador.set("Turno: "+jugador2)
        listaBotones[numero-1].config(state="disable")   
    elif tablero[numero] == "n" and contadorTurno == 2:
        listaBotones[numero-1].config(text="O")
        listaBotones[numero-1].config(bg="lightblue")
        tablero[numero] = "o"
        contadorTurno = 1
        turnoJugador.set("Turno: "+jugador1)
        listaBotones[numero-1].config(state="disable")   
     
# Turno
contadorTurno = 1
# Nombres Jugadores
jugador1 = ""
jugador2 = "Bob La IA"
contadorIATurno = 2

jugadorFicha = 'O'
computadoraIA = 'X'

ventana = Tk()
ventana.geometry("400x500")
ventana.title("Tic Tac Toe Minimax RLPC")

listaBotones = [] #Botones del tablero
turnoJugador = StringVar()

# x, o , n nada
tablero = {1: 'n', 2: 'n', 3: 'n',
           4: 'n', 5: 'n', 6: 'n',
           7: 'n', 8: 'n', 9: 'n'}


boton0 = Button(ventana,width=9,height=3,command=lambda: cambiar(1))
boton1 = Button(ventana,width=9,height=3,command=lambda: cambiar(2))
boton2 = Button(ventana,width=9,height=3,command=lambda: cambiar(3))
boton3 = Button(ventana,width=9,height=3,command=lambda: cambiar(4))
boton4 = Button(ventana,width=9,height=3,command=lambda: cambiar(5))
boton5 = Button(ventana,width=9,height=3,command=lambda: cambiar(6))
boton6 = Button(ventana,width=9,height=3,command=lambda: cambiar(7))
boton7 = Button(ventana,width=9,height=3,command=lambda: cambiar(8))
boton8 = Button(ventana,width=9,height=3,command=lambda: cambiar(9))

boton0.place(x=50,y=50)
boton1.place(x=150,y=50)
boton2.place(x=250,y=50)
boton3.place(x=50,y=150)
boton4.place(x=150,y=150)
boton5.place(x=250,y=150)
boton6.place(x=50,y=250)
boton7.place(x=150,y=250)
boton8.place(x=250,y=250)

listaBotones.append(boton0)
listaBotones.append(boton1)
listaBotones.append(boton2)
listaBotones.append(boton3)
listaBotones.append(boton4)
listaBotones.append(boton5)
listaBotones.append(boton6)
listaBotones.append(boton7)
listaBotones.append(boton8)

turnoEtiqueta = Label(ventana,textvariable=turnoJugador).place(x=120,y=20)

iniciar = Button(ventana, bg="#006", fg="white",text="Iniciar Juego", width=15,height=3,command=iniciaJuego).place(x=120,y=350)

bloquearBotones()
ventana.mainloop()



