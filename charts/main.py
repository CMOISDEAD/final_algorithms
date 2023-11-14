import seaborn as sns
import matplotlib.pyplot as plt


def leer_tiempos(file_path):
    tiempos = {}
    with open(file_path, "r") as file:
        for line in file:
            nombre, tiempo_str = line.strip().split(": ")
            tiempo = float(tiempo_str)
            tiempos[nombre] = tiempo
    return tiempos


def graficar_tiempos(tiempos):
    sns.set(style="whitegrid")
    plt.figure(figsize=(10, 6))
    ax = sns.barplot(
        x=list(tiempos.keys()), y=list(tiempos.values()), palette="viridis"
    )
    ax.set(xlabel="Algoritmo", ylabel="Tiempo (ms)")
    plt.title("Tiempo de ejecución de algoritmos")
    plt.show()


if __name__ == "__main__":
    archivo_tiempos = "./archivo.txt"
    tiempos = leer_tiempos(archivo_tiempos)
    graficar_tiempos(tiempos)
