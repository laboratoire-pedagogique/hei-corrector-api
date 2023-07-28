export function sumIntervals(intervals) {
    // in there is no interval at all
    if (intervals.length === 0){
        return 0;
    }
    // Sort intervals by their start values
    intervals.sort((a, b) => a[0] - b[0]);

    let totalLength = 0;
    let currentInterval = intervals[0];

    for (let i = 1; i < intervals.length; i++) {
        const nextInterval = intervals[i];

        // If the current interval overlaps with the next interval, merge them
        if (currentInterval[1] >= nextInterval[0]) {
            currentInterval[1] = Math.max(currentInterval[1], nextInterval[1]);
        } else {
            // Non-overlapping interval found, add its length to the total
            totalLength += currentInterval[1] - currentInterval[0];
            currentInterval = nextInterval;
        }
    }

    // Add the length of the last interval to the total
    totalLength += currentInterval[1] - currentInterval[0];

    return totalLength;
}