import { test, expect } from '@playwright/test';

test('has header', async ({ page }) => {
  await page.goto('http://localhost:3000');

  // The title should be visible
  await expect(page).toHaveTitle(/Wookieepedia/);

  // Both people and planets tabs should be visible
  await expect(page.getByRole('tab', { name: '🤖 PEOPLE' })).toBeVisible();
  await expect(page.getByRole('tab', { name: '🪐 PLANETS' })).toBeVisible();
});


test('people happy path', async ({ page }) => {
  await page.goto('http://localhost:3000');

  // Luke Skywalker should be visible
  await expect(page.getByText('Luke Skywalker')).toBeVisible();
  await expect(page.getByText('Gasgano')).not.toBeVisible();

  const peopleSearchBar = page.getByPlaceholder('Search people...');
  await peopleSearchBar.fill("gano");

  // Gasgano should be visible while Luke Skywalker is not
  await expect(page.getByText('Gasgano')).toBeVisible();
  await expect(page.getByText('Luke Skywalker')).not.toBeVisible();
});

test('planets happy path', async ({ page }) => {
  await page.goto('http://localhost:3000');

  await page.getByRole('tab', { name: '🪐 PLANETS' }).click();

  await expect(page.getByText('Tatooine')).toBeVisible();
  await expect(page.getByText('Felucia')).not.toBeVisible();

  const planetsSearchBar = page.getByPlaceholder('Search planets...');
  await planetsSearchBar.fill("fe");

  // Expect Felucia to be visible
  await expect(page.getByText('Felucia')).toBeVisible();
  await expect(page.getByText('Tatooine')).not.toBeVisible();
});
