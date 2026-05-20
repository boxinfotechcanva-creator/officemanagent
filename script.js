// Mobile Navigation Toggle
document.addEventListener('DOMContentLoaded', function() {
    const mobileToggle = document.querySelector('.mobile-toggle');
    const navList = document.querySelector('.nav-list');
    const nav = document.querySelector('.nav');

    if (mobileToggle) {
        mobileToggle.addEventListener('click', function() {
            nav.classList.toggle('nav-open');
            const icon = mobileToggle.querySelector('i');
            if (nav.classList.contains('nav-open')) {
                icon.classList.remove('fa-bars');
                icon.classList.add('fa-times');
                nav.style.display = 'block';
                nav.style.position = 'absolute';
                nav.style.top = '100%';
                nav.style.left = '0';
                nav.style.right = '0';
                nav.style.background = '#fff';
                nav.style.boxShadow = '0 4px 15px rgba(0,0,0,0.1)';
                nav.style.padding = '20px';
                nav.style.zIndex = '999';
                navList.style.flexDirection = 'column';
                navList.style.gap = '5px';
            } else {
                icon.classList.remove('fa-times');
                icon.classList.add('fa-bars');
                nav.style.display = '';
                navList.style.flexDirection = '';
                navList.style.gap = '';
            }
        });
    }

    // FAQ Toggle
    const faqQuestions = document.querySelectorAll('.faq-question');
    faqQuestions.forEach(function(question) {
        question.addEventListener('click', function() {
            const icon = this.querySelector('i');
            const faqItem = this.closest('.faq-item');
            const answer = faqItem.querySelector('.faq-answer');

            if (icon.classList.contains('fa-plus')) {
                icon.classList.remove('fa-plus');
                icon.classList.add('fa-minus');
            } else {
                icon.classList.remove('fa-minus');
                icon.classList.add('fa-plus');
            }

            if (answer) {
                answer.style.display = answer.style.display === 'none' ? 'block' : 'none';
            }
        });
    });

    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(function(anchor) {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            var target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });

    // Form submission handler
    var form = document.querySelector('.counselling-form');
    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Thank you for your interest! Our counsellor will contact you shortly.');
            form.reset();
        });
    }

    // Scroll animation for stats
    var statsObserved = false;
    var statsSection = document.querySelector('.stats-bar');

    function animateStats() {
        if (statsObserved) return;
        var statNumbers = document.querySelectorAll('.stat-number');
        statNumbers.forEach(function(stat) {
            stat.style.animation = 'fadeInUp 0.5s ease forwards';
        });
        statsObserved = true;
    }

    if (statsSection && 'IntersectionObserver' in window) {
        var observer = new IntersectionObserver(function(entries) {
            entries.forEach(function(entry) {
                if (entry.isIntersecting) {
                    animateStats();
                }
            });
        }, { threshold: 0.5 });
        observer.observe(statsSection);
    }

    // Header scroll effect
    var header = document.querySelector('.header');
    window.addEventListener('scroll', function() {
        if (window.scrollY > 50) {
            header.style.boxShadow = '0 2px 20px rgba(0,0,0,0.15)';
        } else {
            header.style.boxShadow = '0 2px 10px rgba(0,0,0,0.08)';
        }
    });
});
