export function findUnique(input) {
    const numberCounts = {};

    // Count the occurrences of each number in the input array
    for (let i = 0; i < input.length; i++) {
        const number = input[i];
        if (numberCounts[number]) {
            numberCounts[number]++;
        } else {
            numberCounts[number] = 1;
        }
    }

    // Find the number with a count of 1 (unique number)
    for (const number in numberCounts) {
        if (numberCounts[number] === 1) {
            return Number(number); // Converts back the type of the key from a string to a Number
        }
    }
}