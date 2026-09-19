// Quit date: January 1, 2011, 08:00 local time
// Cigarettes per day: 20 | Minutes per cigarette: 5 | Cost per cigarette: 0.35

var quitDate = new Date(2011, 0, 1, 0, 0, 0);
var now = new Date();
var totalDays = Math.floor((now.getTime() - quitDate.getTime()) / 86400000);

// Saved cigarettes: cigs_per_day × total_days (displayed as plain Int)
output.expectedCigs = String(20 * totalDays);

// Saved time: (min_per_cig × cigs_per_day × total_days) / 60 hours
// App truncates to 2 decimals via: Math.floor(x * 100) / 100
var rawTime = (5 * 20 * totalDays) / 60;
var truncTime = Math.floor(rawTime * 100) / 100;
// Kotlin Double.toString() keeps ".0" for whole numbers; JS drops it — align formats
output.expectedTime = (truncTime % 1 === 0 ? truncTime.toFixed(1) : String(truncTime)) + " h";

// Years since quit — matches kotlinx-datetime periodUntil().years
// Quit date is Jan 1, so every day of any year is past the anniversary.
// Simple subtraction is always correct.
output.expectedYears = String(now.getFullYear() - 2011);
