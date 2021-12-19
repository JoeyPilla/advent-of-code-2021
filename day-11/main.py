def get_nbrs(r, c):
    nbrs = []
    for y, x in [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)]:
        if r + y >= 0 and r + y <= 9 and c + x >= 0 and c + x <= 9:
            nbrs.append((r + y, c + x))
    return nbrs


data = [[int(j) for j in list(line)] for line in open("input.txt").read().strip().split("\n")]
step = 0
flashes = 0
pt1 = 0
pt2 = None
while True:
    flashed = []
    nbrs = []
    # increase energy levels of all
    for r in range(len(data)):
        for c in range(len(data[r])):
            # flash octopus
            if data[r][c] == 9:
                data[r][c] = 0
                flashes += 1
                flashed.append((r, c))
                nbrs.extend(get_nbrs(r, c))
            else:
                data[r][c] += 1
    # increase adjacent octopuses recursivly
    while nbrs:
        nbr_r, nbr_c = nbrs.pop(0)
        if (nbr_r, nbr_c) not in flashed:
            if data[nbr_r][nbr_c] == 9:
                data[nbr_r][nbr_c] = 0
                flashes += 1
                if (nbr_r, nbr_c) not in flashed:
                    flashed.append((nbr_r, nbr_c))
                nbrs.extend(get_nbrs(nbr_r, nbr_c))
            else:
                data[nbr_r][nbr_c] += 1
    # check for answers
    if step + 1 == 100:
        pt1 = int(flashes)
    if len(flashed) == 100:
        pt2 = step + 1
        break

    step += 1

print(f"Part 1: {pt1}")
print(f"Part 2: {pt2}")
